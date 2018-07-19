package cn.pompip.browser.interceptor;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.exception.UnLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = UnLoginException.class)
    public Result HandlerException(HttpServletRequest request, UnLoginException e){
        Result result = new Result();
        result.setCode(2);
        result.setMsg(e.getMessage());
        result.setData("未登录");
        e.printStackTrace();
        return result;
    }
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result HandlerException(HttpServletRequest request, Exception e){
        Result result = new Result();
        result.setCode(1);
        result.setMsg(e.getMessage());
        result.setData(e.getCause());
        e.printStackTrace();
        return result;
    }
}
