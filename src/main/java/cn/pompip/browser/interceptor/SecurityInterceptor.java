/**
 * 
 */
package cn.pompip.browser.interceptor;


import cn.pompip.browser.common.Constant;
import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.UserBean;
import cn.pompip.browser.service.UserService;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.MapUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.json.JSONUtil;
import cn.pompip.browser.util.security.MD5;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/***
 * 拦截器
 * 拦截和客户端的通信数据
 * @author xiejiong
 *
 */
public class SecurityInterceptor implements HandlerInterceptor {
  
	private Log log = LogFactory.getLog(SecurityInterceptor.class);
	
	@Autowired
	private UserService userService;
      
    public SecurityInterceptor() {  
        // TODO Auto-generated constructor stub  
    }  
  
    private String mappingURL;//利用正则映射到需要拦截的路径    
        public void setMappingURL(String mappingURL) {    
               this.mappingURL = mappingURL;    
       }   
  
    /** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     *  
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
    	log.info("request url:"+request.getRequestURL());
    	String url=request.getRequestURL().toString(); 
    	Map<String,String> requestParam=HttpClientUtil.getParameterMap(request);
        requestParam = MapUtil.sortMapByKey(requestParam);
        log.info("request param:"+JSONUtil.toJson(requestParam));
    	if("1".equals(PropertiesFileUtil.getValue("DEBUG"))){
    		log.info("调试模式，通信不加密");
            return true;
        }
//        if (true){
//            return true;
//        }
        if(url!=null&&(url.contains("/activity")||url.contains("/maile/saveOrder.html"))){ 
        	log.info("特殊url，不拦截加密");
             return true;
        }
        Object sign = request.getParameter("sign");
        if(sign == null){
        	log.info("no sign!");
        	response.getWriter().write(JSONUtil.toJson(new Result(0, "Illegal request! No sign!", null)));
        	return false;
        }
        String uid = request.getParameter("uid");
        String SECRET_KEY =  Constant.SECRET_KEY;
        if(uid != null){
        	UserBean user=userService.getById(Integer.parseInt(uid));
        	if(user==null||user.getToken()==null||"".equals(user.getToken()))
        	{
        		log.info("not find user or token!");
            	response.getWriter().write(JSONUtil.toJson(new Result(0, "Illegal request! not find user or token!", null)));  
            	return false;
        	}
        	SECRET_KEY = user.getToken();
        }
        String signStr="";
        Set<String> keySet =requestParam.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if(key.equals("sign"))
        		continue;
        	signStr += key+"="+requestParam.get(key)+"&";
        }
        log.info("sign param:"+signStr+SECRET_KEY);
        String mySign = MD5.getMD5(signStr+SECRET_KEY);
        log.info("serverSign:"+mySign+",clientSign:"+sign.toString());
        if(!sign.equals(mySign))
        {
        	log.info("check sign error!");
        	response.getWriter().write(JSONUtil.toJson(new Result(1, "Illegal request!", null)));
        	return false;
        }
        return true;  
    }  
  
    //在业务处理器处理请求执行完成后,生成视图之前执行的动作   
    @Override  
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub  
//        System.out.println("==============执行顺序: 2、postHandle================"); 
    }  
  
    /** 
     * 在DispatcherServlet完全处理完请求后被调用  
     *  
     *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
    @Override  
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {  
        // TODO Auto-generated method stub  
//        System.out.println("==============执行顺序: 3、afterCompletion================");  
    }  
}  