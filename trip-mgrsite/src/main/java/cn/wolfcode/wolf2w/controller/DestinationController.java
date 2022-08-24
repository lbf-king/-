package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.domain.Destination;
import cn.wolfcode.wolf2w.query.DestinationQuery;
import cn.wolfcode.wolf2w.service.IDestinationService;
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
@RequestMapping("destination")
public class DestinationController {
    @Autowired
    private IDestinationService destinationService;
    @RequestMapping("/list")
    public String list(Model model , @ModelAttribute("qo") DestinationQuery qo) {
        Page<Destination> page = destinationService.queryList(qo);
        model.addAttribute("page",page);
        //通过父级id  查询到下一级目的地的集合
        List<Destination> list = destinationService.queryToastsByParentId(qo.getParentId());
        model.addAttribute("toasts",list);
        return "/destination/list";
    }

    @RequestMapping("/updateInfo")
    @ResponseBody
    public JsonResult updateInfo(Long id, String info) {
        destinationService.updateInfo(id,info);
        return JsonResult.success();
    }
}
