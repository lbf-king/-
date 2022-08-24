package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.annotation.RequiredLogin;
import cn.wolfcode.wolf2w.annotation.UserParam;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.redis.service.IStrategyStatisVOService;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("users")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService redisService;
    @Autowired
    private IStrategyStatisVOService strategyStatisVOService;
    @GetMapping("/detail")
    public UserInfo detail(Long id) {
        return userInfoService.getById(id);
    }

    /**
     * 1>前端 发送ajax请求  带过来一个符合格式的String类型的phone
     * 2>定义方法接收这个phone
     * 3>调用userInfoService中的方法 来从数据库查询 这个phone存在不存在  所以返回值应是布尔类型
     * 4>返回json格式的布尔值
     * @param phone
     * @return  be(存在)
     */
    @GetMapping("/checkPhone")
    public boolean checkPhone(String phone) {
        boolean be = userInfoService.checkPhone(phone);
        return be;
    }

    /**
     * 发送验证码
     */
    @GetMapping("/sendVerifyCode")
    public JsonResult sendVerifyCode (String phone) {
        userInfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }

    @PostMapping("/regist")
    public JsonResult regist (String nickname,String password,String rpassword,String phone,String verifyCode) {
        userInfoService.regist(nickname, password,rpassword, phone, verifyCode);
        return JsonResult.success();
    }

    @PostMapping("/login")
    public JsonResult login (String username,String password) {
        UserInfo user= userInfoService.login(username,password);
        String token = redisService.createTokenAndSave(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        return JsonResult.success(map);
    }


    @RequiredLogin
    @GetMapping ("/currentUser")
    public JsonResult currentUser (HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfo userInfo = redisService.getUserByToken(token);
        return JsonResult.success(userInfo);
    }

    /**
     * 蓝色小星星回显
     * @param sid
     * @param userInfo
     * @return
     */
    @RequiredLogin
    @GetMapping ("/strategies/favor")
    public JsonResult favor (Long sid , @UserParam UserInfo userInfo) {
        boolean b = strategyStatisVOService.isFavorBySis(sid,userInfo.getId());
        return JsonResult.success(b);
    }
}
