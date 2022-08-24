package cn.wolfcode.wolf2w.search.repository;


import cn.wolfcode.wolf2w.search.domain.UserInfoEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoEsRepository extends ElasticsearchRepository<UserInfoEs,String> {
}
