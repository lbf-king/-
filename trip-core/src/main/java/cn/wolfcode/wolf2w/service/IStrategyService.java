package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyContent;
import cn.wolfcode.wolf2w.query.StrategyQuery;
import cn.wolfcode.wolf2w.redis.vo.StrategyStatisVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略服务接口
 */
public interface IStrategyService extends IService<Strategy>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Strategy> queryPage(StrategyQuery qo);

    /**
     * 回显内容文章
     * @param id
     * @return
     */
    StrategyContent getContent(Long id);

    /**
     * 目的地下  概况  显是攻略
     * @param id
     * @return
     */
    List<Strategy> queryByCatalogId(Long id);

    /**
     * 目的地下  攻略  热度 前三
     * @param destId
     * @return
     */
    List<Strategy> queryByDestid(Long destId);

    /**
     * 更新 数据库的数据
     * @param vo
     */
    void updateStatisNum(StrategyStatisVO vo);

    /**
     * 根据目的地id  查询到游记列表
     * @param id
     * @return
     */
    List<Strategy> queryByDestId(Long id);
}
