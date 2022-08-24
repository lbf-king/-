package cn.wolfcode.wolf2w.redis.util;

import cn.wolfcode.wolf2w.util.Consts;
import lombok.Getter;


@Getter
public enum  RedisKeys {
    USER_FOLLOW("user_follow",-1L),
    //点赞数加一
    USER_STRATEGY_THUMBSUPNUM("user_strategy_favor",-1L),
    //收藏实现
    USER_STRATEGY_FAVOR("user_strategy_favor",-1L),
    //阅读数加一
    STRATEGY_STATIS_VO("strategy_statis_vo",-1L),//不设置时间限制  先给个-1
    //短信验证
    PEGIST_VERIF_CODE("pegist_verif_code", Consts.VERIFY_CODE_VAI_TIME*60L),
    //登录  token
    USER_LOGIN_TOKEN("user_login_token",Consts.USER_INFO_TOKEN_VAI_TIME*60L);



    private String prefix;  //前缀
    private Long time;  //时间

    RedisKeys(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    public String join(String ...values) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }
}
