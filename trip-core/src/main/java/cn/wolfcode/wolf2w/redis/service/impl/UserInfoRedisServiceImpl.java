package cn.wolfcode.wolf2w.redis.service.impl;

import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.exception.LogicException;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public StringRedisTemplate get() {
        return template;
    }

    @Override
    public String createTokenAndSave(UserInfo user) {
        //使用uuid获取到唯一的token
        String token = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        template.opsForValue().set(key, JSON.toJSONString(user),RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public UserInfo getUserByToken(String token) {
        if (! StringUtils.hasText(token)) {
            return null;
        }
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        if (template.hasKey(key)) {
            //通过key  获取到value  user
            String user = template.opsForValue().get(key);
            //将json格式的key  转化为对象
            UserInfo userInfo = JSON.parseObject(user, UserInfo.class);
            //设置key的存活时间为30分钟
            template.expire(key,RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
            //将key返回
            return userInfo;
        }
        return null;
    }

    @Override
    public Boolean Foller(Long id, Long userId) {
        //先拼接key
        String key = RedisKeys.USER_FOLLOW.join(id.toString());
        //这个其实是要在定时器中使用并获取strategy表中的所有id 并缓存至redis中
        //暂时先这样
        if (id==userId){
            throw new LogicException("不能关注自己");
        }
        if (template.hasKey(key)){
            HashSet<Long> ids = new HashSet<>();
            template.opsForSet().add(key,"-1");
        }
        //通过key获取redis 中的数据 产看是否包含userId
        //有证明已经关注,且不能关注自己
        if (template.opsForSet().isMember(key,userId.toString())){
            template.opsForSet().remove(key,userId.toString());
        }else{
            //没有表示还没有登录
            template.opsForSet().add(key,userId.toString());
        }
        return template.opsForSet().isMember(key,userId.toString());
    }

    @Override
    public Boolean getFoller(Long id, Long userId) {
        String key = RedisKeys.USER_FOLLOW.join(id.toString());
        if (template.hasKey(key)){
            HashSet<Long> ids = new HashSet<>();
            template.opsForSet().add(key,"-1");
        }
        return template.opsForSet().isMember(key,userId.toString());
    }

    @Override
    public Long followNum(Long id) {
        String key = RedisKeys.USER_FOLLOW.join(id.toString());
        if (!template.hasKey(key)){
            return 0L;
        }
        return template.opsForSet().size(key)-1;
    }

    @Override
    public List<UserInfo> follows(Long userId) {
        List<UserInfo> users = new ArrayList<>();
        String key = RedisKeys.USER_FOLLOW.join(userId.toString());
        Set<String> members = template.opsForSet().members(key);
        for (String member : members) {
            if (!member.equals("-1")) {
                UserInfo byId = userInfoService.getById(Long.parseLong(member));
                users.add(byId);
            }
        }
        return users;
    }
}
