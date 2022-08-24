package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.StrategyContent;
import cn.wolfcode.wolf2w.query.StrategyContentQuery;
import cn.wolfcode.wolf2w.service.IStrategyContentService;
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
* 攻略内容控制层
*/
@Controller
@RequestMapping("strategyContent")
public class StrategyContentController {

    @Autowired
    private IStrategyContentService strategyContentService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyContentQuery qo){
        IPage<StrategyContent> page = strategyContentService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategyContent/list";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyContentService.getById(id));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyContent strategyContent){
        strategyContentService.saveOrUpdate(strategyContent);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyContentService.removeById(id);
        return JsonResult.success();
    }
}
