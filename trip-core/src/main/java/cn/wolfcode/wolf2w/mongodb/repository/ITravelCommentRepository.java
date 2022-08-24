package cn.wolfcode.wolf2w.mongodb.repository;


import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.domain.TravelComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITravelCommentRepository extends MongoRepository<TravelComment,String> {
    List<TravelComment> findByTravelId(Long travelId);
}
