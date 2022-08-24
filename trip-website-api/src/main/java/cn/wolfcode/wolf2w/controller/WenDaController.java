package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.annotation.RequiredLogin;
import cn.wolfcode.wolf2w.domain.WenDa;
import cn.wolfcode.wolf2w.query.WenDaQuery;
import cn.wolfcode.wolf2w.service.IWenDaService;
import cn.wolfcode.wolf2w.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("wenda")
public class WenDaController {
    @Autowired
    private IWenDaService wenDaService;

    @GetMapping("/list")
    public JsonResult list() {
        List<WenDa> list = wenDaService.list();
        return JsonResult.success(list);
    }

    @GetMapping("/hahah")
    public JsonResult detail(Long rid) {
        WenDa byId = wenDaService.getById(rid);
        return JsonResult.success(byId);
    }



}
