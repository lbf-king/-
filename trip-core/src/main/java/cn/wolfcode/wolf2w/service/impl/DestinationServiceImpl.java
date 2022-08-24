package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Region;
import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.mapper.DestinationMapper;
import cn.wolfcode.wolf2w.mapper.RegionMapper;
import cn.wolfcode.wolf2w.query.DestinationQuery;
import cn.wolfcode.wolf2w.service.IDestinationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class DestinationServiceImpl extends ServiceImpl<DestinationMapper, Destination> implements IDestinationService {
    @Autowired
    private DestinationMapper destinationMapper;
    @Autowired
    private RegionMapper regionMapper;

    @Override
    public Page<Destination> queryList(DestinationQuery qo) {
        QueryWrapper<Destination> wrapper = Wrappers.<Destination>query();
        Page<Destination> destinationPage = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        wrapper.like(StringUtils.hasText(qo.getKeyword()),"name",qo.getKeyword());

        //如果 父级id  不为空 查询条件 就是 parent_id=qo.getParentId()
        wrapper.eq(qo.getParentId() != null,"parent_id",qo.getParentId());
        //如果 父级id 为空 查询条件 就是 parent_id=qo.getParentId()
        wrapper.isNull(qo.getParentId() == null,"parent_id");
        return super.page(destinationPage,wrapper);
    }

    @Override
    public List<Destination> queryByRegionId(Long rid) {
        //通过区域id  获取到区域对象
        Region region = regionMapper.selectById(rid);
        //通过区域对象  获取到关联目的地id的集合
        List<Long> ids = region.parseRefIds();
        //通过目的id集合  获取到关联目的地的集合
        List<Destination> list = destinationMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void updateInfo(Long id, String info) {
        UpdateWrapper<Destination> wrapper = Wrappers.<Destination>update();
        if (StringUtils.hasText(info)) {
            wrapper.eq("id",id);
            wrapper.set("info",info);
            super.update(wrapper);
        }
    }

    @Override
    public List<Destination> queryToastsByParentId(Long parentId) {
        List<Destination> list = new ArrayList<>();

        this.queryToasts(list,parentId);

        //使集合里的元素翻转
        //根 >> 东城区 >> 北京 >> 中国----------->根 >> 中国 >> 北京 >> 东城区
        Collections.reverse(list);

        return list;
    }

    public void queryToasts(List<Destination> list,Long parentId) {
        //先书写结束递归的方法
        if (parentId == null) {
            return;
        }

        //通过本目的父级的id  查询到上一级目的地
        Destination destination = super.getById(parentId);
        //添加带list中
        list.add(destination);

        //如果上一级目的地  一直能获取到父级id  就一直 递归
        if (destination.getParentId() != null) {
            this.queryToasts(list,destination.getParentId());
        }
    }

    @Override
    public List<Destination> search(Long regionId) {
        List<Destination> list = new ArrayList<>();
        QueryWrapper<Destination> wrapper = Wrappers.<Destination>query();
        if (regionId == -1) {
            wrapper.eq("parent_id",1L);
            list = destinationMapper.selectList(wrapper);
        } else {
            list = this.queryByRegionId(regionId);
        }


        for (Destination destination : list) {
            wrapper.clear();
            wrapper.eq("parent_id",destination.getId());
            wrapper.last("limit 5");
            List<Destination> destinationList = super.list(wrapper);
            destination.setChildren(destinationList);
        }
        return list;
    }

    @Override
    public Destination getByName(String destName) {
        QueryWrapper<Destination> wrapper = Wrappers.<Destination>query();
        wrapper.eq(destName != null,"name",destName);
        return super.getOne(wrapper);
    }


}
