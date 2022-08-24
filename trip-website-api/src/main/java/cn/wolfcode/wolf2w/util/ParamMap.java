package cn.wolfcode.wolf2w.util;

import org.springframework.data.domain.PageImpl;

import java.util.HashMap;

public class ParamMap extends HashMap<String , Object> {
    public ParamMap put(String key , Object value) {
        super.put(key,value);
        return this;
    }

    public static ParamMap newInstance() {
        return new ParamMap();
    }
}
