package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.MenuBean;
import cn.pompip.browser.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("/menu")
@Controller
public class MenuController {


    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/getMenuList.html")
    public Result getMenuList(HttpServletRequest request) {

        String uid = request.getParameter("uid");
        String phoneType = request.getParameter("phoneType");
        String type = request.getParameter("type");

        List<MenuBean> menuBeans = menuService.queryAllMenu(type);

        return Result.success(menuBeans);

    }
}
