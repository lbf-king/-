package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyCondition;
import cn.wolfcode.wolf2w.mapper.StrategyConditionMapper;
import cn.wolfcode.wolf2w.query.StrategyConditionQuery;
import cn.wolfcode.wolf2w.service.IStrategyConditionService;
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
import java.util.Map;

/**
* 攻略条件统计表服务接口实现
*/
@Service
@Transactional
public class StrategyConditionServiceImpl extends ServiceImpl<StrategyConditionMapper,StrategyCondition> implements IStrategyConditionService  {

    @Autowired
    private IStrategyService strategyService;
    @Override
    public IPage<StrategyCondition> queryPage(StrategyConditionQuery qo) {
        IPage<StrategyCondition> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCondition> wrapper = Wrappers.<StrategyCondition>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<StrategyCondition> queryByType(Integer type) {
        /**
         * -- 攻略条件统计  主题
         * SELECT *
         * FROM strategy_condition
         * WHERE type = 3
         * and statis_time in(SELECT MAX(statis_time) FROM strategy_condition WHERE type=3)
         * ORDER BY count desc
         */
        QueryWrapper<StrategyCondition> wrapper = Wrappers.<StrategyCondition>query();
        wrapper.eq("type",type);
        wrapper.inSql("statis_time","SELECT MAX(statis_time) FROM strategy_condition WHERE type="+type+"");
        wrapper.orderByDesc("count");
        List<StrategyCondition> list = super.list(wrapper);
        return list;
    }

    @Override
    public void conditionDataHandle(Integer type) {
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();
        /**
         * -- 从攻略表 查询到 攻略条件统计 的数据 然后 插入到小表  主题
         *
         * SELECT theme_id refid,theme_name name,count(id) count
         * FROM strategy
         * GROUP BY theme_id,theme_name
         */
        if (type == StrategyCondition.TYPE_THEME) {
            wrapper.select("theme_id refid,theme_name name,count(id) count");
            wrapper.groupBy("theme_id,theme_name");
            /**
             * -- 从攻略表 查询到 攻略条件统计 的数据 然后 插入到小表  国内外
             * SELECT dest_id refid,dest_name name,count(id) count
             * FROM strategy
             * WHERE isabroad=0
             * GROUP BY dest_id,dest_name
             */
        } else if(type == StrategyCondition.TYPE_ABROAD){
            wrapper.eq("isabroad",1);
            wrapper.select("dest_id refid,dest_name name,count(id) count");
            wrapper.groupBy("dest_id,dest_name");
        } else {
            wrapper.eq("isabroad",0);
            wrapper.select("dest_id refid,dest_name name,count(id) count");
            wrapper.groupBy("dest_id,dest_name");
        }

        List<Map<String, Object>> listMaps = strategyService.listMaps(wrapper);
        Date now = new Date();
        ArrayList<StrategyCondition> conditions = new ArrayList<>();
        for (Map<String, Object> listMap : listMaps) {
            StrategyCondition condition = new StrategyCondition();
            condition.setCount(Integer.parseInt(listMap.get("count").toString()));
            condition.setStatisTime(now);
            condition.setName(listMap.get("name").toString());
            condition.setRefid(Long.parseLong(listMap.get("refid").toString()));
            condition.setType(type);
            conditions.add(condition);
        }
        super.saveBatch(conditions);
    }
}
