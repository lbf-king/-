package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.StrategyCondition;
import cn.wolfcode.wolf2w.query.StrategyConditionQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略条件统计表服务接口
 */
public interface IStrategyConditionService extends IService<StrategyCondition>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCondition> queryPage(StrategyConditionQuery qo);

    /**
     * 根据类型  来查询 到页面需要展示的数据
     * @param type
     * @return
     */
    List<StrategyCondition> queryByType(Integer type);

    /**
     * 维护小表数据
     * @param type
     */
    void conditionDataHandle(Integer type);
}
