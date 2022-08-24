package cn.wolfcode.wolf2w.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 攻略条件统计表
 */
@Setter
@Getter
@TableName("strategy_condition")
public class StrategyCondition extends  BaseDomain{

    public static final Integer TYPE_ABROAD = 1;  //国外
    public static final Integer TYPE_CHINA = 2;   //国内
    public static final Integer TYPE_THEME = 3;     //热门

    private String name;
    private Integer count; //个数
    private Long refid; //关联id
    private Integer type; //条件类型
    private Date statisTime; //归档统计时间
}
