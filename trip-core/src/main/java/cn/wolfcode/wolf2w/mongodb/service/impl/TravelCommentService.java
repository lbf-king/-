package cn.wolfcode.wolf2w.mongodb.service.impl;
import cn.wolfcode.wolf2w.mongodb.domain.StrategyComment;
import cn.wolfcode.wolf2w.mongodb.domain.TravelComment;
import cn.wolfcode.wolf2w.mongodb.repository.ITravelCommentRepository;
import cn.wolfcode.wolf2w.mongodb.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
public class TravelCommentService implements ITravelCommentService {
    @Autowired
    private ITravelCommentRepository travelCommentRepository;
    @Override
    public void save(TravelComment travelComment) {
        travelComment.setId(null);
        travelComment.setCreateTime(new Date());


        //维护关联评论
        String refId = travelComment.getRefComment().getId();
        if (StringUtils.hasText(refId)) {
            //评论的评论
            TravelComment comment = travelCommentRepository.findById(refId).get();
            //将评论的评论  绑定 到 评论上
            travelComment.setRefComment(comment);
            travelComment.setType(TravelComment.TRAVLE_COMMENT_TYPE);
        } else {
            //普通的评论
            travelComment.setType(TravelComment.TRAVLE_COMMENT_TYPE_COMMENT);
        }

        travelCommentRepository.save(travelComment);
    }

    @Override
    public List<TravelComment> queryByTravelId(Long travelId) {
        return travelCommentRepository.findByTravelId(travelId);
    }
}
