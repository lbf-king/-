package cn.wolfcode.wolf2w.redis.service;

import cn.wolfcode.wolf2w.domain.UserInfo;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public interface IUserInfoRedisService {
    StringRedisTemplate get();

    /**
     * 获取token
     * @param user  将user对象  转换为json格式的字符串 作为value 存入redis
     * @return 返回key token
     */
    String createTokenAndSave(UserInfo user);

    UserInfo getUserByToken(String token);


    Boolean Foller(Long id, Long userId);
    Boolean getFoller(Long id, Long userId);

    Long followNum(Long id);

    List<UserInfo> follows(Long userId);

}
