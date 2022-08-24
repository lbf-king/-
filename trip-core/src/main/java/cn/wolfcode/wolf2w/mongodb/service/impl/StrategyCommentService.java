package cn.wolfcode.wolf2w.mongodb.service.impl;

import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.mongodb.repository.IStrategyCommentRepository;
import cn.wolfcode.wolf2w.mongodb.service.IStrategyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class StrategyCommentService implements IStrategyCommentService {
    @Autowired
    private IStrategyCommentRepository strategyCommentRepository;
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(StrategyComment comment) {
        comment.setId(null);
        comment.setCreateTime(new Date());
        strategyCommentRepository.save(comment);
    }

    @Override
    public Page<StrategyComment> queryPage(StrategyCommentQuery qo) {

        //计算出count
        //new 出查询对象
        Query query = new Query();
        //如果传过来的攻略id  不为空
        if(qo.getStrategyId() != null) {
            //那就 把 列 strategyId  是  qo.getStrategyId()的 这条数据查询出来
            query.addCriteria(Criteria.where("strategyId").is(qo.getStrategyId()));
        }
        //计算出count值
        long count = template.count(query, StrategyComment.class);

        if (count == 0) {
            return Page.empty();
        }

        //计算出list
        //利用PageRequest对象  点上  of 方法  将 当前页 和 每页显示条数  多少条丢进去
        //当前页  默认是  0
        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize());
        //查询对象  绑定 pageRequest
        query.with(pageRequest);
        //查询到  所有的数据  并分页
        List<StrategyComment> list = template.find(query, StrategyComment.class);
        //new Page实现类的对象  并 返回回去
        PageImpl<StrategyComment> page = new PageImpl<>(list, pageRequest, count);
        return page;
    }

}
