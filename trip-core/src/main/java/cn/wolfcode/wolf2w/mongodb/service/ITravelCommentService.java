package cn.wolfcode.wolf2w.mongodb.service;


import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.domain.TravelComment;

import java.util.List;

public interface ITravelCommentService {

    /**
     * 添加  游记 评论
     * @param travelComment
     */
    void save(TravelComment travelComment);

    /**
     * 显示评论列表
     * @param travelId
     * @return
     */
    List<TravelComment> queryByTravelId(Long travelId);
}
