package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.ClientVersionBean;
import cn.pompip.browser.service.ClientVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/clientVersion")
@Controller
public class ClientVersionController {

    @Autowired
    private ClientVersionService clientVersionService;

    @ResponseBody
    @RequestMapping(value = "/checkVersion")
    public Result checkVersion(HttpServletRequest request) {
        String type = request.getParameter("type");
        ClientVersionBean version = this.clientVersionService.selectByType(Integer.parseInt(type));
        return Result.success(version);

    }

}
