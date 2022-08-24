package cn.wolfcode.wolf2w.mongodb.service;

import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.query.StrategyCommentQuery;
import org.springframework.data.domain.Page;

public interface IStrategyCommentService {
    /**
     * 添加  评论
     * @param comment
     */
    void save(StrategyComment comment);

    /**
     * 评论分页  页面展示
     * @param qo
     * @return
     */
    Page<StrategyComment> queryPage(StrategyCommentQuery qo);

}
