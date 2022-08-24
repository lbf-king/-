package cn.wolfcode.wolf2w.exception;

import cn.wolfcode.wolf2w.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LogicException.class)
    @ResponseBody
    public Object logicException(LogicException e, HttpServletResponse response) {
        e.printStackTrace();
        response.setContentType("application/json;charset:utf-8");
        return JsonResult.error(JsonResult.CODE_ERROR,e.getMessage(),null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Object runtimeException(RuntimeException e, HttpServletResponse response) {
        e.printStackTrace();
        response.setContentType("application/json;charset:utf-8");
        return JsonResult.defaultError();
    }


}
