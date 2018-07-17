package cn.pompip.browser.aspect;

import cn.pompip.browser.util.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Aspect
@Component
public class HttpAspect {
    @Autowired
    ObjectMapper mapper ;

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * cn.pompip.browser.controller..*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuilder stringBuilder = new StringBuilder("url="+request.getRequestURI()+"  ");
        Map<String, String[]> parameterMap = request.getParameterMap();

        parameterMap.forEach((key,value) ->{
            String v = "";
            if (value.length>0){
               v = value[0];
            }
            stringBuilder.append(key).append(":").append(v).append("  ");
        });
        logger.info(stringBuilder.toString());


    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturning(Object object) throws JsonProcessingException {
        logger.info(mapper.writeValueAsString(object));

    }

}
