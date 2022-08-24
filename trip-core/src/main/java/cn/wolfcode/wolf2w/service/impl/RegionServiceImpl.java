package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Region;
import cn.wolfcode.wolf2w.mapper.RegionMapper;
import cn.wolfcode.wolf2w.query.RegionQuery;
import cn.wolfcode.wolf2w.service.IRegionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public Page<Region> queryList(RegionQuery qo) {
        QueryWrapper<Region> wrapper = Wrappers.<Region>query();
        Page<Region> regionPage = new Page<>(qo.getCurrentPage(),qo.getPageSize());

        return super.page(regionPage,wrapper);
    }

    @Override
    public void changeHotValue(Long id, int hot) {
        UpdateWrapper<Region> wrapper = Wrappers.<Region>update();
        wrapper.eq("id",id);
        wrapper.set("ishot",hot);
        super.update(wrapper);

    }

    @Override
    public List<Region> hotRegion() {

        QueryWrapper<Region> wrapper = Wrappers.<Region>query();
        wrapper.eq("ishot",Region.STATE_HOT);
        //给热门城市排序
        wrapper.orderBy(true,true,"seq");
        return super.list(wrapper);
    }
}
