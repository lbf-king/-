package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.query.DestinationQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IDestinationService extends IService<Destination> {

    /**
     * 显示列表 并分页
     * @param qo
     * @return
     */
    Page<Destination> queryList(DestinationQuery qo);

    /**
     * 通过 区域id  获取到 目的地集合
     * @param rid
     * @return
     */
    List<Destination> queryByRegionId(Long rid);

    /**
     * 目的地管理页面 修改备注
     * @param id
     * @param info
     */
    void updateInfo(Long id, String info);

    /**
     * 吐司查询
     * @param parentId  父级id
     * @return
     */
    List<Destination> queryToastsByParentId(Long parentId);


    /**
     * 根据区域id  查找热门城市
     * @param regionId
     * @return
     */
    List<Destination> search(Long regionId);


    /**
     * 根据用户在搜索框输入的 目的地名来查询到目的地对象
     * @param destName
     * @return
     */
    Destination getByName(String destName);

}
