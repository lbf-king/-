package cn.wolfcode.wolf2w.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 攻略主题
 */
@Setter
@Getter
@TableName("strategy_theme")
public class StrategyTheme extends BaseDomain {
    public static final int STATE_NORMAL = 0; //正常
    public static final int STATE_DISABLE = 1; //禁用

    private String name;  //主题名称
    private int state = STATE_NORMAL; //主题状态

    private int seq; //序号
    public String getStateDisplay(){
        return state == STATE_NORMAL ? "正常" : "禁用";
    }
    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("state",state);
        map.put("seq",seq);
        return JSON.toJSONString(map);
    }
}