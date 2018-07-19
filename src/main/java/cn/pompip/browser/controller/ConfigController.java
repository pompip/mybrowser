package cn.pompip.browser.controller;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.ConfigBean;
import cn.pompip.browser.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/config")
@Controller
public class ConfigController {


    @Autowired
    private ConfigService configService;

    @ResponseBody
    @RequestMapping(value = "/getConfigByType")
    public Result getConfigByType(HttpServletRequest request) {
        String type = request.getParameter("type");
        ConfigBean config = this.configService.selectByType(Integer.parseInt(type));
        return Result.success(config);

    }

}
