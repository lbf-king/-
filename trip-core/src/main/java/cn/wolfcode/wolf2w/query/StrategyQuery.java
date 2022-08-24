package cn.wolfcode.wolf2w.query;


import cn.wolfcode.wolf2w.domain.Strategy;
import lombok.Getter;
import lombok.Setter;

/**
* 攻略查询参数封装对象
*/
@Setter
@Getter
public class StrategyQuery extends  QueryObject{
    private Long themeId;
    private Long destId;


    private Long refid;
    private Integer type;

    private String orderBy;
}
