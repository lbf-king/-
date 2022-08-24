package cn.wolfcode.wolf2w.mongodb.query;

import cn.wolfcode.wolf2w.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StrategyCommentQuery extends QueryObject {
    private Long strategyId;
}
