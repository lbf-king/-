package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Region;
import cn.wolfcode.wolf2w.query.RegionQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IRegionService extends IService<Region> {

    Page<Region> queryList(RegionQuery qo);

    /**
     * 修改是否热门
     * @param id
     * @param hot
     */
    void changeHotValue(Long id, int hot);


    /**
     * 查询热门区域
     * @return
     */
    List<Region> hotRegion();


}
