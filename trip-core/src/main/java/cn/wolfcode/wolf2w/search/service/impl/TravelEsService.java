package cn.wolfcode.wolf2w.search.service.impl;

import cn.wolfcode.wolf2w.search.domain.TravelEs;
import cn.wolfcode.wolf2w.search.repository.TravelEsRepository;
import cn.wolfcode.wolf2w.search.service.ITravelEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelEsService implements ITravelEsService {
    @Autowired
    private TravelEsRepository travelEsRepository;

    @Override
    public void save(TravelEs strategy) {
        travelEsRepository.save(strategy);
    }

    @Override
    public void update(TravelEs strategy) {
        travelEsRepository.save(strategy);
    }

    @Override
    public void delete(String id) {
        travelEsRepository.deleteById(id);
    }

    @Override
    public TravelEs get(String id) {
        return travelEsRepository.findById(id).get();
    }

    @Override
    public List<TravelEs> list() {
        Iterable<TravelEs> all = travelEsRepository.findAll();
        List<TravelEs> p = new ArrayList<>();
        all.forEach(a->p.add(a));
        return p;
    }
}
