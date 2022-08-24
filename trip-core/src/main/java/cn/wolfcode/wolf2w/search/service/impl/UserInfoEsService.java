package cn.wolfcode.wolf2w.search.service.impl;

import cn.wolfcode.wolf2w.search.domain.UserInfoEs;
import cn.wolfcode.wolf2w.search.repository.UserInfoEsRepository;
import cn.wolfcode.wolf2w.search.service.IUserInfoEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoEsService implements IUserInfoEsService {
    @Autowired
    private UserInfoEsRepository userInfoEsRepository;

    @Override
    public void save(UserInfoEs strategy) {
        userInfoEsRepository.save(strategy);
    }

    @Override
    public void update(UserInfoEs strategy) {
        userInfoEsRepository.save(strategy);
    }

    @Override
    public void delete(String id) {
        userInfoEsRepository.deleteById(id);
    }

    @Override
    public UserInfoEs get(String id) {
        return userInfoEsRepository.findById(id).get();
    }

    @Override
    public List<UserInfoEs> list() {
        Iterable<UserInfoEs> all = userInfoEsRepository.findAll();
        List<UserInfoEs> p = new ArrayList<>();
        all.forEach(a->p.add(a));
        return p;
    }
}
