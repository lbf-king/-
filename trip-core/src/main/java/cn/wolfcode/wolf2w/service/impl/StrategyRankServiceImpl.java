package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyRank;
import cn.wolfcode.wolf2w.mapper.StrategyRankMapper;
import cn.wolfcode.wolf2w.query.StrategyRankQuery;
import cn.wolfcode.wolf2w.service.IStrategyRankService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* 攻略排行服务接口实现
*/
@Service
@Transactional
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper,StrategyRank> implements IStrategyRankService  {

    @Autowired
    private IStrategyService strategyService;
    @Override
    public IPage<StrategyRank> queryPage(StrategyRankQuery qo) {
        IPage<StrategyRank> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyRank> wrapper = Wrappers.<StrategyRank>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<StrategyRank> queryByType(Integer type) {
        /**
         * SELECT * FROM strategy_rank
         * WHERE type = 3
         * and statis_time in (SELECT MAX(statis_time) FROM strategy_rank where type = 3)
         * ORDER BY statisnum DESC
         * LIMIT 10
         */
        QueryWrapper<StrategyRank> wrapper = Wrappers.<StrategyRank>query();
        //如果 in里面只有一个值的话   等值于  =  而这里用eq会报sql语法错误
        wrapper.inSql("statis_time","SELECT MAX(statis_time) FROM strategy_rank where type = "+type+"");
        wrapper.eq("type",type);
        //只显示10条数据
        wrapper.last("limit 10");
        return super.list(wrapper);
    }

    @Override
    public void rankDataHandle(Integer type) {
    /**
     * SELECT id,title,dest_id,dest_name,viewnum  + replynum
     * FROM strategy
     * ORDER BY viewnum  + replynum DESC
     * limit 10
     */
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        //如果 如果传过来的类型  是热门推荐
        if (type.equals(StrategyRank.TYPE_HOT)) {
            //查询的排序 按 浏览数和 评论数 来 排序
            wrapper.orderByDesc("viewnum  + replynum");
            ////如果 如果传过来的类型  是国内攻略
        } else if (type.equals(StrategyRank.TYPE_CHINA)) {
            wrapper.eq("isabroad",0);
            //查询的排序 按 点赞数和 收藏数 来 排序
            wrapper.orderByDesc("thumbsupnum  + favornum");
            //如果 如果传过来的类型  是国外攻略
        } else if(type.equals(StrategyRank.TYPE_ABROAD)) {
            wrapper.eq("isabroad",1);
            wrapper.orderByDesc("thumbsupnum  + favornum");
        }
        wrapper.last("limit 10");
        List<Strategy> strategyList = strategyService.list(wrapper);
        Date now = new Date();
        ArrayList<StrategyRank> list = new ArrayList<>();
        for (Strategy strategy : strategyList) {
            StrategyRank rank = new StrategyRank();
            rank.setDestId(strategy.getDestId());
            rank.setDestName(strategy.getDestName());
            rank.setStrategyTitle(strategy.getTitle());
            rank.setStrategyId(strategy.getId());
            rank.setType(type);
            rank.setStatisTime(now);
            if(type.equals(StrategyRank.TYPE_HOT)) {
                rank.setStatisnum((long) strategy.getViewnum() + strategy.getReplynum());
            } else {
                rank.setStatisnum((long) strategy.getThumbsupnum() + strategy.getFavornum());
            }
            list.add(rank);
        }
        super.saveBatch(list);
    }
}
