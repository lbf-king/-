package cn.wolfcode.wolf2w.listener;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.redis.service.IStrategyStatisVOService;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import cn.wolfcode.wolf2w.service.IStrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 使用 框架自带的监听器 来  初始化  数据
 */
//@Component
public class RedisDataInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyStatisVOService strategyStatisVOService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("初始化-------------------------------------------开始");

        List<Strategy> list = strategyService.list();   //建议查出来的攻略都是热门的  不然 sql可能挂掉


        if (list != null && list.size()>0) {
            for (Strategy strategy : list) {
                //判断这个key  存不存在  如果存在 跳过本次循环
                if (strategyStatisVOService.isVoExists(strategy.getId())) {
                    continue;
                }
                StrategyStatisVO vo = new StrategyStatisVO();
                BeanUtils.copyProperties(strategy,vo);
                vo.setStrategyId(strategy.getId());
                strategyStatisVOService.setStatisVO(vo);
            }
        }
        System.out.println("初始化-------------------------------------------结束");
    }
}
