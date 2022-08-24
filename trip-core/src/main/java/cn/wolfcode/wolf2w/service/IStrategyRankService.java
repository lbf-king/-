package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.StrategyRank;
import cn.wolfcode.wolf2w.query.StrategyRankQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略排行服务接口
 */
public interface IStrategyRankService extends IService<StrategyRank>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyRank> queryPage(StrategyRankQuery qo);

    /**
     * 热门 攻略 排行 查询
     * @param type 根据 类型查询
     * @return 返回 查询到的列表
     */
    List<StrategyRank> queryByType(Integer type);

    void rankDataHandle(Integer type);
}
