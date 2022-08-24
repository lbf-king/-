package cn.wolfcode.wolf2w.util;

import cn.wolfcode.wolf2w.exception.LogicException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.security.auth.login.LoginException;

public class AssertUtils {
    private AssertUtils() {};

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new LogicException(message);
        }
    }

    public static void isEquals(String p1,String p2) {
        if (p1 == null || p2 == null) {
            throw new LogicException("密码不能为空");
        }
        if (! p1.equals(p2)) {
            throw new LogicException("两次输入密码必须一致");
        }
    }
}
