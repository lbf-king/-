package cn.wolfcode.wolf2w.search.service.impl;

import cn.wolfcode.wolf2w.search.domain.StrategyEs;
import cn.wolfcode.wolf2w.search.repository.StrategyEsRepository;
import cn.wolfcode.wolf2w.search.service.IStrategyEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StrategyEsService implements IStrategyEsService {
    @Autowired
    private StrategyEsRepository strategyEsRepository;

    @Override
    public void save(StrategyEs strategy) {
        strategyEsRepository.save(strategy);
    }

    @Override
    public void update(StrategyEs strategy) {
        strategyEsRepository.save(strategy);
    }

    @Override
    public void delete(String id) {
        strategyEsRepository.deleteById(id);
    }

    @Override
    public StrategyEs get(String id) {
        return strategyEsRepository.findById(id).get();
    }

    @Override
    public List<StrategyEs> list() {
        Iterable<StrategyEs> all = strategyEsRepository.findAll();
        List<StrategyEs> p = new ArrayList<>();
        all.forEach(a->p.add(a));
        return p;
    }
}
