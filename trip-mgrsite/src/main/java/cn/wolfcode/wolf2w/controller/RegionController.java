package cn.wolfcode.wolf2w.controller;


import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.domain.Region;
import cn.wolfcode.wolf2w.query.RegionQuery;
import cn.wolfcode.wolf2w.service.IDestinationService;
import cn.wolfcode.wolf2w.service.IRegionService;
import cn.wolfcode.wolf2w.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("region")
public class RegionController {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDestinationService destinationService;

    @RequestMapping("/list")
    public String list(Model model , @ModelAttribute("qo") RegionQuery qo) {

        Page<Region> page = regionService.queryList(qo);
        model.addAttribute("page",page);
        List<Destination> dests = destinationService.list();
        model.addAttribute("dests",dests);
        return "/region/list";
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Region region) {
        regionService.saveOrUpdate(region);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        regionService.removeById(id);
        return JsonResult.success();
    }

    @RequestMapping("/changeHotValue")
    @ResponseBody
    public JsonResult changeHotValue(Long id,int hot) {
        regionService.changeHotValue(id,hot);
        return JsonResult.success();
    }

    @RequestMapping("/getDestByRegionId")
    @ResponseBody
    public List<Destination> getDestByRegionId(Long rid) {
        List<Destination> list = destinationService.queryByRegionId(rid);
        return list;
    }



}
