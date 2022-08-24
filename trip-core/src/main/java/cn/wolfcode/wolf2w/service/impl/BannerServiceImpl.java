package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Banner;
import cn.wolfcode.wolf2w.mapper.BannerMapper;
import cn.wolfcode.wolf2w.query.BannerQuery;
import cn.wolfcode.wolf2w.service.IBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 首页服务接口实现
*/
@Service
@Transactional
public class BannerServiceImpl extends ServiceImpl<BannerMapper,Banner> implements IBannerService  {

    @Override
    public IPage<Banner> queryPage(BannerQuery qo) {
        IPage<Banner> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Banner> wrapper = Wrappers.<Banner>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<Banner> queryByType(int type) {
        QueryWrapper<Banner> wrapper = Wrappers.<Banner>query();
        wrapper.eq("type",type);
        wrapper.eq("state",Banner.STATE_NORMAL);
        wrapper.orderByAsc("seq");
        wrapper.last("limit 5");
        return super.list(wrapper);
    }
}
