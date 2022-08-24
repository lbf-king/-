package cn.wolfcode.wolf2w.search.service;

import cn.wolfcode.wolf2w.search.domain.DestinationEs;


import java.util.List;

public interface IDestinationEsService {
    void save(DestinationEs destinationEs);
    void update(DestinationEs destinationEs);
    void delete(String id);
    DestinationEs get(String id);
    List<DestinationEs> list();
}
