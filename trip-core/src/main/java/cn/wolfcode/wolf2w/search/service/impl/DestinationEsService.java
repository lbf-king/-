package cn.wolfcode.wolf2w.search.service.impl;

import cn.wolfcode.wolf2w.search.domain.DestinationEs;
import cn.wolfcode.wolf2w.search.repository.DestinationEsRepository;
import cn.wolfcode.wolf2w.search.service.IDestinationEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationEsService implements IDestinationEsService {
    @Autowired
    private DestinationEsRepository destinationEsRepository;
    
    @Override
    public void save(DestinationEs strategy) {
        destinationEsRepository.save(strategy);
    }

    @Override
    public void update(DestinationEs strategy) {
        destinationEsRepository.save(strategy);
    }

    @Override
    public void delete(String id) {
        destinationEsRepository.deleteById(id);
    }

    @Override
    public DestinationEs get(String id) {
        return destinationEsRepository.findById(id).get();
    }

    @Override
    public List<DestinationEs> list() {
        Iterable<DestinationEs> all = destinationEsRepository.findAll();
        List<DestinationEs> p = new ArrayList<>();
        all.forEach(a->p.add(a));
        return p;
    }
}
