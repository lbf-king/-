package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Strategy;
import cn.wolfcode.wolf2w.domain.StrategyContent;
import cn.wolfcode.wolf2w.query.StrategyQuery;
import cn.wolfcode.wolf2w.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.service.IStrategyService;
import cn.wolfcode.wolf2w.service.IStrategyThemeService;
import cn.wolfcode.wolf2w.vo.CatalogVo;
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
* 攻略控制层
*/
@Controller
@RequestMapping("strategy")
public class StrategyController {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyQuery qo){
        IPage<Strategy> page = strategyService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategy/list";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyService.getById(id));
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Strategy strategy){
        strategyService.saveOrUpdate(strategy);
        return JsonResult.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyService.removeById(id);
        return JsonResult.success();
    }

    @RequestMapping("/input")
    public String input(Model model ,Long id){

        //编辑回显
        if (id != null) {
            Strategy strategy = strategyService.getById(id);
            StrategyContent content = strategyService.getContent(id);
            strategy.setContent(content);
            model.addAttribute("strategy", strategy);
        }




        //themes  主题
        model.addAttribute("themes",strategyThemeService.list());

        //catalogs  添加页面 攻略分类 下拉框
        List<CatalogVo> catalogs = strategyCatalogService.queryGroup();
        model.addAttribute("catalogs",catalogs);
        return "strategy/input";
    }
}
