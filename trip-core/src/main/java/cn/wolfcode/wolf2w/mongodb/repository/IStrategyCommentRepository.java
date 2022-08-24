package cn.wolfcode.wolf2w.mongodb.repository;

import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStrategyCommentRepository extends MongoRepository<StrategyComment,String> {
}
