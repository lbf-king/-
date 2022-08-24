package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.StrategyTheme;
import cn.wolfcode.wolf2w.query.StrategyThemeQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 攻略主题服务接口
 */
public interface IStrategyThemeService extends IService<StrategyTheme>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyTheme> queryPage(StrategyThemeQuery qo);
}
