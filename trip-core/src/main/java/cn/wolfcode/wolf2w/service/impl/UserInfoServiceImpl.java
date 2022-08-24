package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.exception.LogicException;
import cn.wolfcode.wolf2w.mapper.UserInfoMapper;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import cn.wolfcode.wolf2w.util.AssertUtils;
import cn.wolfcode.wolf2w.util.Consts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService redisService;

    @Autowired
    private StringRedisTemplate template;

    @Override
    public boolean checkPhone(String phone) {
        QueryWrapper<UserInfo> wrapper = Wrappers.<UserInfo>query();
        wrapper.eq("phone",phone);
        UserInfo userInfo = userInfoService.getOne(wrapper);
        return userInfo != null;
    }

    @Override
    public void sendVerifyCode(String phone) {
        //使用uuid 获取到4位验证码   使用replaceAll 方法 去掉 - 换成""  再截取前四位  得到验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);

        StringRedisTemplate template = redisService.get();
        //设置  redis 库中的key
        String codeKey = RedisKeys.PEGIST_VERIF_CODE.join(phone);
        template.opsForValue().set(codeKey,code, Consts.VERIFY_CODE_VAI_TIME * 60L, TimeUnit.SECONDS);
        String note = "验证码" + code + ",限时" + Consts.VERIFY_CODE_VAI_TIME + "分钟";
        System.out.println(note);

        //发送验证码
        String aapkey = "2498dd072fc36f09df62e9dc4295242e";
        String url = "https://way.jd.com/chuangxin/dxjk?mobile={1}&content=【创信】你的验证码是：{2}，3分钟内有效！&appkey={3}";

        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.getForObject(url, String.class, phone, code, aapkey);
        System.out.println(s);

        //如果短信中不包含success  说明短信发送失败
        if (! s.contains("Success")) {
            throw new LogicException("短信发送失败");
        }
    }

    @Override
    public void regist(String nickname, String password,String rpassword, String phone, String verifyCode) {
        //判断所有的参数是否为空
        AssertUtils.hasText(nickname,"内容不能为空");
        AssertUtils.hasText(password,"内容不能为空");
        AssertUtils.hasText(rpassword,"内容不能为空");
        AssertUtils.hasText(phone,"内容不能为空");
        AssertUtils.hasText(verifyCode,"内容不能为空");
        //判断两次输入的密码是否一致
        AssertUtils.isEquals(password,rpassword);
        //判断手机号格式是否正确 @TODO  判断手机号格式是否正确  先放放 有时间再搞

        //判断手机号是否唯一
        if (this.checkPhone(phone)) {
            throw new LogicException("手机号已经被注册");
        }
        //判断验证码是否一致
        StringRedisTemplate template = redisService.get();
        String codeKey = RedisKeys.PEGIST_VERIF_CODE.join(phone);
        String code = template.opsForValue().get(codeKey);
        if (! code.equals(verifyCode)) {
            throw new LogicException("验证码不正确");
        }

        //封装参数
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setPassword(password);
        userInfo.setPhone(phone);
        userInfo.setState(UserInfo.STATE_NORMAL);
        userInfo.setHeadImgUrl("/images/default.jpg");
        userInfoService.save(userInfo);
    }

    @Override
    public UserInfo login(String username, String password) {
        QueryWrapper<UserInfo> wrapper = Wrappers.<UserInfo>query();
        wrapper.eq("phone",username);
        wrapper.eq("password",password);
        UserInfo user = userInfoService.getOne(wrapper);
        //如果user为空 则 抛出异常   如果账号处于冻结状态  抛出异常
        if (user == null) {
            throw new LogicException("账号或者密码有误");
        } else if(user.getState().equals(UserInfo.STATE_DISABLE)) {
            throw new LogicException("账号被冻结");
        }
        return user;
    }

    @Override
    public List<UserInfo> queryByDestid(String city) {
        QueryWrapper<UserInfo> wrapper = Wrappers.<UserInfo>query();
        wrapper.like("city",city);
        return super.list(wrapper);
    }



}
