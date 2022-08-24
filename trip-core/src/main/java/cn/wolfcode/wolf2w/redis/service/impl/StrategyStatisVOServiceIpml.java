package cn.wolfcode.wolf2w.redis.service.impl;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.redis.service.IStrategyStatisVOService;
import cn.wolfcode.wolf2w.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.util.DateUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.lang.model.element.VariableElement;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class StrategyStatisVOServiceIpml implements IStrategyStatisVOService {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private IStrategyService strategyService;
    @Override
    public void viewnumIncr(Long id) {
        //利用枚举类 和id 拼接出key
        String key= RedisKeys.STRATEGY_STATIS_VO.join(id.toString());

        StrategyStatisVO vo = this.queryStatisVO(id);

        //对vo对象 中 的阅读数加一
        vo.setViewnum(vo.getViewnum() + 1);

        //并更新 redis中的数据
        template.opsForValue().set(key,JSON.toJSONString(vo));
    }

    @Override
    public StrategyStatisVO queryStatisVO(Long sid) {
        //利用枚举类 和id 拼接出key
        String key= RedisKeys.STRATEGY_STATIS_VO.join(sid.toString());

        StrategyStatisVO vo = null;

        //判断 redis中是否有 这个key
        if (template.hasKey(key)) {
            //如果有 通过这个key  获取到对应的value也就是StrategyStatisVO对象
            String voStr = template.opsForValue().get(key);

            vo = JSON.parseObject(voStr,StrategyStatisVO.class);
        } else {
            //如果没有  就通过这个key  创建并缓存到redis中
            //new 一个 vo对象
            vo = new StrategyStatisVO();

            //获取到  id 对应的 strategy对象
            Strategy strategy = strategyService.getById(sid);

            //通过  框架提供的BeanUtils工具类的方法  copyProperties  将 源对象 的 相同属性值  复制到目标对象上
            BeanUtils.copyProperties(strategy,vo);

            //因为 没有 两个对象的 字段名不一样  所以 手动设置 攻略id
            vo.setStrategyId(strategy.getId());

            //将json格式的vo对象  存到redis
            template.opsForValue().set(key,JSON.toJSONString(vo));
        }
        return vo;
    }

    @Override
    public void setStatisVO(StrategyStatisVO vo) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(vo.getStrategyId().toString());
        template.opsForValue().set(key,JSON.toJSONString(vo));
    }

    @Override
    public void replynumIncr(Long id) {
        //利用枚举类 和id 拼接出key

        StrategyStatisVO vo = this.queryStatisVO(id);

        //对vo对象 中 的阅读数加一
        vo.setReplynum(vo.getReplynum() + 1);

        //并更新 redis中的数据
        this.setStatisVO(vo);
    }

    @Override
    public boolean favor(Long sid,Long uid) {
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(uid.toString());
        //判断 key  是否存在  如果存在 不做任何 操作  如果不存在 将key缓存到redis中
        if (! template.hasKey(key)) {
            //后续  从mysql中查询到所有的 攻略id 的集合  都在这里存着
            Set<StrategyStatisVO> set = new HashSet<>();
            //暂时 存个 -1 因为  如果 set中没有数据的话  redis会自动将这个 key移除掉
            template.opsForSet().add(key,"-1");
        }


        StrategyStatisVO vo = this.queryStatisVO(sid);
        //判断 这个key对应的value攻略id集合  有没有这个key
        if (template.opsForSet().isMember(key,sid.toString())) {
            //如果在  攻略id集合移除掉这个id  获取vo对象  收藏数减一
            template.opsForSet().remove(key,sid.toString());

            vo.setFavornum(vo.getFavornum() - 1);
        } else {
            //如果不在  攻略id结合 添加这个id  获取vo对象  收藏数加一
            template.opsForSet().add(key,sid.toString());
            vo.setFavornum(vo.getFavornum() + 1);
        }
        //更新 redis 中的数据
        this.setStatisVO(vo);
        return template.opsForSet().isMember(key,sid.toString());
    }

    @Override
    public boolean isFavorBySis(Long sid, Long uid) {
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(uid.toString());
        //判断这个
        return template.opsForSet().isMember(key,sid.toString());
    }

    @Override
    public boolean strategyThumbup(Long sid, Long uid) {
        String key = RedisKeys.USER_STRATEGY_THUMBSUPNUM.join(uid.toString(),sid.toString());
        if (!template.hasKey(key)) {

            Date now = new Date();
            //这个方法的作用返回一个今天的最后时间  23:59:59
            Date endDate = DateUtil.getEndDate(now);
            //当天最后时间的时间戳 减去 现在时间的时间戳  除以1000  的绝对值
            long time = DateUtil.getDateBetween(endDate, now);
            //如果不存在  缓存 一个 key  并设置 时效到今天最后一秒
            template.opsForValue().set(key,"1",time, TimeUnit.SECONDS);
            //获取到vo对象
            StrategyStatisVO vo = this.queryStatisVO(sid);
            //点赞数加一
            vo.setThumbsupnum(vo.getThumbsupnum() +1);
            //更新redis中对应的vo的数据
            this.setStatisVO(vo);
            return true;
        }
        return false;
    }

    @Override
    public boolean isVoExists(Long id) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(id.toString());
        return template.hasKey(key);
    }

    @Override
    public List<StrategyStatisVO> queryByPattern(String join) {
        List<StrategyStatisVO> vos = new ArrayList<>();
        //获取到  key  组成的集合
        Set<String> keys = template.keys(join);
        //遍历keys  获取到对应的value
        if (keys !=null && keys.size() > 0) {
            for (String key : keys) {
                String vo = template.opsForValue().get(key);
                vos.add(JSON.parseObject(vo,StrategyStatisVO.class));
            }
        }
        return vos;
    }

}
