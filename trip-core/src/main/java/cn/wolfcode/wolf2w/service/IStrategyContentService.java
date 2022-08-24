package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.StrategyContent;
import cn.wolfcode.wolf2w.query.StrategyContentQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 攻略内容服务接口
 */
public interface IStrategyContentService extends IService<StrategyContent>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyContent> queryPage(StrategyContentQuery qo);
}
