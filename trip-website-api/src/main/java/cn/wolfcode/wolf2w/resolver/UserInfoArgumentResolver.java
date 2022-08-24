package cn.wolfcode.wolf2w.resolver;

import cn.wolfcode.wolf2w.annotation.UserParam;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.redis.service.IUserInfoRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现HandlerMethodArgumentResolver  接口
 * 明确指定 需要解析的类型
 */
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    //注入 userInfoRedisService对象
    @Autowired
    private IUserInfoRedisService userInfoRedisService;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == UserInfo.class
                &&
                //  明确指定  需要解析的类型 是 UserInfo并且参数上 贴有UserParam注解返回值  才为true
                methodParameter.hasParameterAnnotation(UserParam.class);
    }

    /**
     *只有当上面的方法  返回true的时候  这个方法才会执行
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        //获取到 请求对象
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        //注入 userInfoRedisService 对象 通过 从请求头拿出来的token 将用户信息 获取出来
        String token = request.getHeader("token");
        UserInfo user = userInfoRedisService.getUserByToken(token);
        return user;
    }
}
