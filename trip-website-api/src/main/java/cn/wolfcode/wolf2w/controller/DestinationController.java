package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.domain.Travel;
import cn.wolfcode.wolf2w.service.*;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("destinations")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;

    @Autowired
    private IRegionService regionService;
    @GetMapping("/hotRegion")
    public JsonResult hotRegion() {
        return JsonResult.success(regionService.hotRegion());
    }


    @GetMapping("/search")
    public JsonResult search(Long regionId) {
        List<Destination>  list = destinationService.search(regionId);
        return JsonResult.success(list);
    }

    @GetMapping("/toasts")
    public JsonResult toasts(Long destId) {
        List<Destination>  list = destinationService.queryToastsByParentId(destId);
        return JsonResult.success(list);
    }

    /**
     * 概况分类
     * @param destId
     * @return
     */
    @GetMapping("/catalogs")
    public JsonResult catalogs(Long destId) {
        //通过  目的地id  获取到 攻略分类集合
        List<StrategyCatalog> list = strategyCatalogService.queryByDestid(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/strategies/viewnumTop3")
    public JsonResult strategiesViewnumTop3(Long destId) {
        List<Strategy> list = strategyService.queryByDestid(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/travels/viewnumTop3")
    public JsonResult travelsViewnumTop3(Long destId) {
        List<Travel> list = travelService.queryByDestid(destId);
        return JsonResult.success(list);
    }





}
