package cn.wolfcode.wolf2w.search.service;

import cn.wolfcode.wolf2w.search.domain.UserInfoEs;

import java.util.List;

public interface IUserInfoEsService {
    void save(UserInfoEs userInfoEs);
    void update(UserInfoEs userInfoEs);
    void delete(String id);
    UserInfoEs get(String id);
    List<UserInfoEs> list();
}
