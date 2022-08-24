package cn.wolfcode.wolf2w.query;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
* 游记查询参数封装对象
*/
@Setter
@Getter
public class TravelQuery extends  QueryObject{

    //目的地下  查询
    private Long destId;

    //按出行天数查询
    private Integer dayType;

    // 按 出发  时间 查询
    private Integer travelTimeType;

    // 按人均消费 查询
    private Integer consumeType;
}
