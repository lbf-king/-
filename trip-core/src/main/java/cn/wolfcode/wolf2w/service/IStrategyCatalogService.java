package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.vo.CatalogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略分类服务接口
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo);

    /**
     * 查询 分类
     * @return
     */
    List<CatalogVo> queryGroup();


    /**
     * 目的地明细  概况 查询分类
     * @param destId
     * @return
     */
    List<StrategyCatalog> queryByDestid(Long destId);


}
