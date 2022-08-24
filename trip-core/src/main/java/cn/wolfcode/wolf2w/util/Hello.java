package cn.wolfcode.wolf2w.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class Hello implements HttpSessionListener {
    private static long count = 0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        count ++ ;
        System.out.println("在线人数" + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        count --;
    }


}
