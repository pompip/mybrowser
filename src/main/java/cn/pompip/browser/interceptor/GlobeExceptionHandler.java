package cn.pompip.browser.interceptor;

import cn.pompip.browser.common.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobeExceptionHandler {


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
