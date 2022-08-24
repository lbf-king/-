package cn.wolfcode.wolf2w.search.query;

import cn.wolfcode.wolf2w.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchQueryObject extends QueryObject {

    public static final int TYPE_ALL = -1;  //所有
    public static final int TYPE_DEST = 0;  //目的地
    public static final int TYPE_STRATEGY = 1;  //攻略
    public static final int TYPE_TRAVEL = 2;  //游记
    public static final int TYPE_USER = 3;  //用户

    private int type = TYPE_ALL;
}
