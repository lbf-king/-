package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.annotation.UserParam;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hz-Liu
 * @create 2022-08-22 15:13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    @GetMapping("/get")
    public JsonResult get(Long userId){
        UserInfo byId = userInfoService.getById(userId);
        return JsonResult.success(byId);
    }
    //进行存储
    @GetMapping("/Follow")
    public JsonResult Follow(@UserParam UserInfo user,Long userId){
        Boolean falg = userInfoRedisService.Foller(user.getId(),userId);
        return JsonResult.success(falg);
    }
    //判断是否有关注该用户
    @GetMapping("/isFollow")
    public JsonResult isFollow(@UserParam UserInfo user,Long userId){
        Boolean falg = userInfoRedisService.getFoller(user.getId(),userId);
        return JsonResult.success(falg);
    }
    @GetMapping("/followNum")
    public JsonResult followNum(Long userId){
        Long num = userInfoRedisService.followNum(userId);
        return JsonResult.success(num);
    }

    @GetMapping("/follows")
    public JsonResult follows(Long userId){
        List<UserInfo> users = userInfoRedisService.follows(userId);
        return JsonResult.success(users);
    }

}