package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.StrategyTheme;
import cn.wolfcode.wolf2w.query.StrategyThemeQuery;
import cn.wolfcode.wolf2w.service.IStrategyThemeService;
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
* 攻略主题控制层
*/
@Controller
@RequestMapping("strategyTheme")
public class StrategyThemeController {

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyThemeQuery qo){
        IPage<StrategyTheme> page = strategyThemeService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategyTheme/list";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyThemeService.getById(id));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyTheme strategyTheme){
        strategyThemeService.saveOrUpdate(strategyTheme);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyThemeService.removeById(id);
        return JsonResult.success();
    }
}
