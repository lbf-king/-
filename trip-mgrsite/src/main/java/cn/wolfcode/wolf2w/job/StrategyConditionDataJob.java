package cn.wolfcode.wolf2w.job;

import cn.wolfcode.wolf2w.domain.StrategyCondition;
import cn.wolfcode.wolf2w.domain.StrategyRank;
import cn.wolfcode.wolf2w.service.IStrategyConditionService;
import cn.wolfcode.wolf2w.service.IStrategyRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class StrategyConditionDataJob {
    @Autowired
    private IStrategyConditionService strategyConditionService;

    // @Scheduled  定时任务调度注解
    //cron 任务调度计划  什么时候执行定时任务
    //在SpringBoot中  没有年  * 代表每   ? 不知道
    //  秒 分  时  日  月  周
    //  5  *   *   *   *   ?
    @Scheduled(cron = "0/5 * * * * ?")
    public void doWork() {
        System.out.println("开始维护");
        strategyConditionService.conditionDataHandle(StrategyCondition.TYPE_THEME);
        strategyConditionService.conditionDataHandle(StrategyCondition.TYPE_ABROAD);
        strategyConditionService.conditionDataHandle(StrategyCondition.TYPE_CHINA);
        System.out.println("结束维护");
    }
}
