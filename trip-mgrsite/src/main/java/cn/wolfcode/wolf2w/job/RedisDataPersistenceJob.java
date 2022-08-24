package cn.wolfcode.wolf2w.job;

import cn.wolfcode.wolf2w.redis.service.IStrategyStatisVOService;
import cn.wolfcode.wolf2w.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import cn.wolfcode.wolf2w.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class RedisDataPersistenceJob {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyStatisVOService strategyStatisVOService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void doWork() {

        System.out.println("持久化-------------------------------------------开始");
        List<StrategyStatisVO> keys = strategyStatisVOService.queryByPattern(RedisKeys.STRATEGY_STATIS_VO.join("*"));

        for (StrategyStatisVO vo : keys) {
            strategyService.updateStatisNum(vo);
        }

        System.out.println("持久化-------------------------------------------开始");
    }
}
