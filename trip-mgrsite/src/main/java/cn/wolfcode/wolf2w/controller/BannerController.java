package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Banner;
import cn.wolfcode.wolf2w.query.BannerQuery;
import cn.wolfcode.wolf2w.service.IBannerService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.ITravelService;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 首页控制层
*/
@Controller
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") BannerQuery qo){
        IPage<Banner> page = bannerService.queryPage(qo);
        model.addAttribute("page", page);
        return "banner/list";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(bannerService.getById(id));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Banner banner){
        bannerService.saveOrUpdate(banner);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        bannerService.removeById(id);
        return JsonResult.success();
    }

    @RequestMapping("/getAllType")
    @ResponseBody
    public JsonResult getAllType(){
        Map<String , Object> map = new HashMap<>();
        map.put("sts",strategyService.list());
        map.put("ts",travelService.list());
        return JsonResult.success(map);
    }

}
