package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
* 攻略分类控制层
*/
@Controller
@RequestMapping("strategyCatalog")
public class StrategyCatalogController {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyCatalogQuery qo){
        IPage<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategyCatalog/list";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyCatalogService.getById(id));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyCatalog strategyCatalog){
        strategyCatalogService.saveOrUpdate(strategyCatalog);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyCatalogService.removeById(id);
        return JsonResult.success();
    }
}
