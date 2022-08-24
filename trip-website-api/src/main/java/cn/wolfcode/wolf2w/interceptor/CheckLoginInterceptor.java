package cn.wolfcode.wolf2w.interceptor;

import cn.wolfcode.wolf2w.annotation.RequiredLogin;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import cn.wolfcode.wolf2w.util.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        /**
         * HandlerMethod  请求映射方法信息封装对象  (封装的信息 方法名  方法参数 方法url路径  方法解析)
         * 1>spring  容器启动的时候 会解析 所有的请求映射方法   将解析出来的所有的方法的所有的信息 封装到HandlerMethod对象
         * 2>为了 方法 管理 这些HandlerMethod对象  Spring使用类似map的集合进行封装
         * key:请求的映射地址  url   value:url对应的请求映射方法对应的HandlerMethod对象
         * 3>当客户端发起请求时   所有的请求都进入前端控制器   在那里会解析这个请求  得到url地址  比如users/currentUser
         *
         * 4>将得到的url地址 去跟所有的拦截器 进行拦截规则匹配  一旦url地址 符合拦截规则匹配  表示拦截器 会拦截当前请求
         * 5>一旦判定某个拦截器  要拦截 某个请求  那么会调用拦截器 preHandle
         *
         * map.get(url请求路径)  返回HandlerMethod对象  handler
         * boolean ret = checkLoginInterceptor.handler(HttpServletRequest request, HttpServletResponse response, Object handler)
         *
         *   if(ret) {
         *       放行
         *   } else{
         *       拦截
         *   }
         *
         *
         */

        //如果请求不是  HandlerMethod的 实例  说明请求是预请求 直接放行
        //真实请求  才执行下边流程
        //配置拦截器的跨域访问
        if (! ( handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        String token = request.getHeader("token");
        UserInfo user = userInfoRedisService.getUserByToken(token);
        if (method.hasMethodAnnotation(RequiredLogin.class)) {
            if (user == null) {
                //如果没有登录  拦截 不给访问
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(JsonResult.noLogin()));
                return false;
            }
        }
        return true;
    }
}
