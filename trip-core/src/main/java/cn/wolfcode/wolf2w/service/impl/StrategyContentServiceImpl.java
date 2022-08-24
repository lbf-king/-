package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.StrategyContent;
import cn.wolfcode.wolf2w.mapper.StrategyContentMapper;
import cn.wolfcode.wolf2w.query.StrategyContentQuery;
import cn.wolfcode.wolf2w.service.IStrategyContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 攻略内容服务接口实现
*/
@Service
@Transactional
public class StrategyContentServiceImpl extends ServiceImpl<StrategyContentMapper,StrategyContent> implements IStrategyContentService  {

    @Override
    public IPage<StrategyContent> queryPage(StrategyContentQuery qo) {
        IPage<StrategyContent> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyContent> wrapper = Wrappers.<StrategyContent>query();
        return super.page(page, wrapper);
    }
}
