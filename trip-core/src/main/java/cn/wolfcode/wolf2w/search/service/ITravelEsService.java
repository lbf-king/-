package cn.wolfcode.wolf2w.search.service;

import cn.wolfcode.wolf2w.search.domain.TravelEs;

import java.util.List;

public interface ITravelEsService {
    void save(TravelEs travelEs);
    void update(TravelEs travelEs);
    void delete(String id);
    TravelEs get(String id);
    List<TravelEs> list();
}
