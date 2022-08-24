package cn.wolfcode.wolf2w.query;



import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class TravelCondition {
    //出行 天数
    public static final  Map<Integer , TravelCondition> DAY_MAP = new HashMap<>();

    //出发时间
    public static final  Map<Integer , TravelCondition> TRAVELTIME_MAP = new HashMap<>();

    //人均消费
    public static final  Map<Integer , TravelCondition> CONSUME_MAP = new HashMap<>();

    static {
        //出行 天数
        DAY_MAP.put(1,new TravelCondition(0,3));
        DAY_MAP.put(2,new TravelCondition(4,7));
        DAY_MAP.put(3,new TravelCondition(8,14));
        DAY_MAP.put(4,new TravelCondition(15,Integer.MAX_VALUE));


        //出发时间
        TRAVELTIME_MAP.put(1,new TravelCondition(1,2));
        TRAVELTIME_MAP.put(2,new TravelCondition(3,4));
        TRAVELTIME_MAP.put(3,new TravelCondition(5,6));
        TRAVELTIME_MAP.put(4,new TravelCondition(7,8));
        TRAVELTIME_MAP.put(5,new TravelCondition(9,10));
        TRAVELTIME_MAP.put(6,new TravelCondition(11,12));


        //人均消费
        CONSUME_MAP.put(1,new TravelCondition(1,999));
        CONSUME_MAP.put(2,new TravelCondition(1000,6000));
        //加1  是为了 取等
        CONSUME_MAP.put(3,new TravelCondition(6001,20000));
        CONSUME_MAP.put(4,new TravelCondition(20001,Integer.MAX_VALUE));
    }

    private Integer min;
    private Integer max;

    public TravelCondition(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }
}
