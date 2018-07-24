package cn.pompip.browser.controller;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.MenuBean;
import cn.pompip.browser.model.PartnerBean;
import cn.pompip.browser.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/partner")
@Controller
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @ResponseBody
    @RequestMapping(value = "/getPartnerList")
    public Result getPartnerList(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String phoneType = request.getParameter("phoneType");

        PartnerBean partner = new PartnerBean();
        partner.setStatus(0);
        List<PartnerBean> list = partnerService.queryList(partner);
        for(int i=0;i<24;i++){
            PartnerBean menuBean = new PartnerBean();
            menuBean.setTitle("hello world");
            menuBean.setUrl("http://m.baidu.com");
            menuBean.setIcon("http://baidu.com/favicon.ico");
            list.add(menuBean);
        }

        return Result.success(list);

    }
}
