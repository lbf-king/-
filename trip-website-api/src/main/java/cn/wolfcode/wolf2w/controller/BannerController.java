package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Banner;
import cn.wolfcode.wolf2w.service.IBannerService;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("banners")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/travel")
    public JsonResult travel() {
        List<Banner> list = bannerService.queryByType(Banner.TYPE_TRAVEL);
        return JsonResult.success(list);
    }

    @GetMapping("/strategy")
    public JsonResult strategy() {
        List<Banner> list = bannerService.queryByType(Banner.TYPE_STRATEGY);
        return JsonResult.success(list);
    }
}
