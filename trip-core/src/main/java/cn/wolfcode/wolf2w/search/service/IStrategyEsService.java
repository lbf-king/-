package cn.wolfcode.wolf2w.search.service;

import cn.wolfcode.wolf2w.search.domain.StrategyEs;

import java.util.List;

public interface IStrategyEsService {
    void save(StrategyEs strategyEs);
    void update(StrategyEs strategyEs);
    void delete(String id);
    StrategyEs get(String id);
    List<StrategyEs> list();
}
