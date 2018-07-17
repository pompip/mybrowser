package cn.pompip.browser.controller;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.util.Cache.LocalCache;
import cn.pompip.browser.util.sms.SmsUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/sms")
@Controller
public class SmsController {


    @ResponseBody
    @RequestMapping(value = "/getVerCode.html")
    public Result getNewList(HttpServletRequest request) throws ClientException {
        Result result = new Result();
        String phoneNum = request.getParameter("phoneNum");
        String type = request.getParameter("type");//传入type 1注册  2验证   3其他
        if (phoneNum == null || type == null) {
            result.setCode(1);
            result.setMsg("参数缺失");
            return result;
        }
        int code = (int) (Math.random() * 9000 + 1000);

        //初始化acsClient,暂不支持region化
        SendSmsResponse sendSmsResponse = SmsUtil.sendAliSms(phoneNum, code);
        if ("OK".equals(sendSmsResponse.getCode())) {
            LocalCache.getInStance().setLocalCache(phoneNum + "_" + type, code, 180000);
            result.setCode(0);
            return result;
        } else {
            result.setCode(1);
            result.setMsg(sendSmsResponse.getCode());
            return result;
        }

    }

}
