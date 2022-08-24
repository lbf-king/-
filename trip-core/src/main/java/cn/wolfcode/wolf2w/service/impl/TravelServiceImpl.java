package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.domain.TravelContent;
import cn.wolfcode.wolf2w.exception.LogicException;
import cn.wolfcode.wolf2w.mapper.TravelContentMapper;
import cn.wolfcode.wolf2w.mapper.TravelMapper;
import cn.wolfcode.wolf2w.query.TravelCondition;
import cn.wolfcode.wolf2w.query.TravelQuery;
import cn.wolfcode.wolf2w.service.ITravelService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* 游记服务接口实现
*/
@Service
@Transactional
public class TravelServiceImpl extends ServiceImpl<TravelMapper,Travel> implements ITravelService  {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private TravelContentMapper travelContentMapper;
    @Override
    public IPage<Travel> queryPage(TravelQuery qo) {
        IPage<Travel> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query();
        //查询  目的地下的 游记明细
        wrapper.eq(qo.getDestId() != null,"dest_id",qo.getDestId());


        //按出行天数查询
        //根据 页面传过来的天数类型 从 map里 获取到对应得value(TravelCondition对象)
        TravelCondition day = TravelCondition.DAY_MAP.get(qo.getDayType());
        //如果天数不为空
        if (day != null) {
            //查询的区间 就是  TravelCondition对象所存的最小天数 到 最大天数
            wrapper.between( "day",day.getMin(),day.getMax());
        }

        //按出行月份查询
        //根据 页面传过来的月份类型 从 map里 获取到对应得value(TravelCondition对象)
        TravelCondition traveltime = TravelCondition.TRAVELTIME_MAP.get(qo.getTravelTimeType());
        //如果月份不为空
        if (traveltime != null) {
            //查询的区间 就是  TravelCondition对象所存的最小月数 到 最大月数
            wrapper.between( "MONTH(travel_time)",traveltime.getMin(),traveltime.getMax());
        }

        //按人均消费查询
        //根据 页面传过来的人均消费类型 从 map里 获取到对应得value(TravelCondition对象)
        TravelCondition consume = TravelCondition.CONSUME_MAP.get(qo.getConsumeType());
        //如果人均消费不为空
        if (consume != null) {
            //查询的区间 就是  TravelCondition对象所存的最小钱数 到 最大钱数
            wrapper.between( "avg_consume",consume.getMin(),consume.getMax());
        }


        super.page(page,wrapper);
        //页面显示 作者名字  遍历分页记录
        for (Travel travel : page.getRecords()) {
            //设置userInfo对象
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        return page;
    }

    @Override
    public void auditByIdAndState(Long id, Integer state) {
        //如果  传过来的状态为空  抛出异常
        if (state == null) {
            throw new LogicException("非法操作");
        }
        Travel travel = super.getById(id);
        //如果 传过来的状态  为 审核通过
        if (state == Travel.STATE_RELEASE) {
            //修改状态
            travel.setState(Travel.STATE_RELEASE);
            //修改最后发布时间
            travel.setReleaseTime( new Date());
            //修改最后更新时间
            travel.setLastUpdateTime( new Date());
            //修改
            super.updateById(travel);
        }



        //如果 传过来的状态  为 审核拒绝
        //如果 传过来的状态  为 审核通过
        if (state == Travel.STATE_REJECT) {
            //修改状态
            travel.setState(Travel.STATE_REJECT);
            //修改最后发布时间
            //travel.setReleaseTime();
            //修改最后更新时间
            travel.setLastUpdateTime( new Date());

            //修改
            super.updateById(travel);
        }

    }

    /**
     * 查看游记内容
     * @param id
     * @return
     */
    @Override
    public TravelContent getContentById(Long id) {
        return travelContentMapper.selectById(id);
    }

    @Override
    public List<Travel> queryByDestid(Long destId) {
        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query();
        wrapper.eq("dest_id",destId);
        wrapper.orderByDesc("viewnum");
        wrapper.last("limit 3");
        return super.list(wrapper);
    }

    @Override
    public List<Travel> queryByDestId(Long id) {
        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query();
        wrapper.eq("dest_id",id);

        List<Travel> list = super.list(wrapper);
        for (Travel travel : list) {
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        return list;

    }
}
