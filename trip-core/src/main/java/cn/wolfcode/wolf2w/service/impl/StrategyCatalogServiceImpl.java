package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.mapper.StrategyCatalogMapper;
import cn.wolfcode.wolf2w.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.vo.CatalogVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.plaf.synth.SynthUI;
import javax.swing.text.html.ObjectView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 攻略分类服务接口实现
*/
@Service
@Transactional
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper,StrategyCatalog> implements IStrategyCatalogService  {

    @Autowired
    private IStrategyService strategyService;
    @Override
    public IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo) {
        IPage<StrategyCatalog> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCatalog> wrapper = Wrappers.<StrategyCatalog>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<CatalogVo> queryGroup() {
        ArrayList<CatalogVo> list = new ArrayList<>();
        /**group_concat 将group by产生的同一个分组中的值连接起来，返回一个字符串结果。
         * select dest_name ,group_concat(id) ids,group_concat(name) names
         * from strategy_catalog
         * group by dest_name
         */

        QueryWrapper<StrategyCatalog> wrapper = Wrappers.<StrategyCatalog>query();
        wrapper.select("dest_name ,group_concat(id) ids,group_concat(name) names");
        wrapper.groupBy("dest_name");

        //将查询到的数据  以键值对的形式 封装在集合中
        List<Map<String, Object>> listMaps = super.listMaps(wrapper);

        //遍历集合  将ids和names 取出来  分割 装到集合中 封装在ov对象 存到list 返回给前端input界面
        for (Map<String, Object> map : listMaps) {
            ///遍历集合  将从数据库中获取到的数据   依次拿出来
            String dest_name = map.get("dest_name").toString();
            String ids = map.get("ids").toString();
            String names = map.get("names").toString();

            //分割 字符串  封装到 StrategyCatalog对象  存到集合
            List<StrategyCatalog> catalongList = this.parseCatalongList(ids,names);
            CatalogVo vo = new CatalogVo();
            //将 目的地名字和 攻略分类id和攻略分类名字组成的集合 封装到 vo对象
            vo.setDestName(dest_name);
            vo.setCatalogList(catalongList);

            //将vo对象  加到 list
            list.add(vo);
        }


        return list;
    }

    //分割 ids 和 names
    private List<StrategyCatalog> parseCatalongList(String ids, String names) {
        List<StrategyCatalog> list =new ArrayList<>();
        if (StringUtils.hasText(ids) && StringUtils.hasText(names)) {
            String[] id = ids.split(",");
            String[] name = names.split(",");
            for (int i = 0; i < id.length; i++) {
                StrategyCatalog strategyCatalog = new StrategyCatalog();
                strategyCatalog.setId(Long.parseLong(id[i]));
                strategyCatalog.setName(name[i]);
                //将分割出来的数据  封装到 strategyCatalog对象  存到list中
                list.add(strategyCatalog);
            }
        }
        return list;
    }


    @Override
    public List<StrategyCatalog> queryByDestid(Long destId) {
        QueryWrapper<StrategyCatalog> wrapper = Wrappers.<StrategyCatalog>query();
        wrapper.eq("dest_id",destId);
        List<StrategyCatalog> list = super.list(wrapper);
        //查询每个分类下的攻略明细
        for (StrategyCatalog catalog : list) {
            List<Strategy> strategyList = strategyService.queryByCatalogId(catalog.getId());
            //将  攻略明细 绑定到 分类 上一起 返回给前端
            catalog.setStrategies(strategyList);
        }
        return list;
    }




}

