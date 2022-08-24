package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.annotation.RequiredLogin;
import cn.wolfcode.wolf2w.annotation.UserParam;
import cn.wolfcode.wolf2w.domain.*;
import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.domain.TravelComment;
import cn.wolfcode.wolf2w.mongodb.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.mongodb.query.TravelCommentQuery;
import cn.wolfcode.wolf2w.mongodb.service.ITravelCommentService;
import cn.wolfcode.wolf2w.query.TravelQuery;


import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.service.ITravelService;

import cn.wolfcode.wolf2w.service.IUserInfoService;
import cn.wolfcode.wolf2w.util.JsonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("travels")
public class TravelController {
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    @Autowired
    private ITravelCommentService travelCommentService;

    @GetMapping("/content")
    //查询目的地下的概况
    public JsonResult content(Long id) {
        return JsonResult.success(travelService.getContentById(id));
    }

    @GetMapping("/detail")
    //查询 攻略明细
    public JsonResult detail(Long id) {
        //查询到攻略明细对象
        Travel travel = travelService.getById(id);
        //获取到文章内容
        TravelContent content = travelService.getContentById(id);
        //将文章内容 设置到 明细对象中 返回给前端
        travel.setContent(content);
        //将用户信息  绑定到 游记对象 上 一起 返回给前端
        UserInfo userInfo = userInfoService.getById(travel.getAuthorId());
        travel.setAuthor(userInfo);
        return JsonResult.success(travel);
    }


    /**
     *分页
     */
    @GetMapping("/query")
    public JsonResult query(TravelQuery qo) {
        IPage<Travel> page = travelService.queryPage(qo);
        return JsonResult.success(page);
    }


    /**
     * 自定义  SpringMvc参数解析器
     * @param userInfo  前端  传过来 token  但是用user对象来接收    此时 就需要自定义  SpringMvc参数解析器才可以达到这个效果
     * @return  返回 一个  user信息
     */
    @RequiredLogin
    @GetMapping("/info")
    public JsonResult info(@UserParam UserInfo userInfo) {
        return JsonResult.success(userInfo);
    }

    //测试  贴不贴注解的区别
    @RequiredLogin
    @GetMapping("/userInfo")
    public JsonResult userInfo(UserInfo userInfo) {
        return JsonResult.success(userInfo);
    }


    @PostMapping("commentAdd")
    public JsonResult condition(TravelComment travelComment, @UserParam UserInfo userInfo) {

        //将user对象  中的信息  复制到  strategyComment对象中
        BeanUtils.copyProperties(userInfo,travelComment);
        travelComment.setUserId(userInfo.getId());

        travelCommentService.save(travelComment);

        return JsonResult.success();
    }



    @GetMapping("/comments")
    public JsonResult comments(Long travelId) {
        List<TravelComment> list = travelCommentService.queryByTravelId(travelId);
        return JsonResult.success(list);
    }

}
