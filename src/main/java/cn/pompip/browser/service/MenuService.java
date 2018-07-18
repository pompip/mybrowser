package cn.pompip.browser.service;


import cn.pompip.browser.dao.MenuDao;
import cn.pompip.browser.model.MenuBean;
import cn.pompip.browser.task.GetNewListTask;
import cn.pompip.browser.task.GetVideoListTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDao dao;


    public List<MenuBean> queryAllMenu(String type) {
        ArrayList<MenuBean> menuBeans = new ArrayList<>();

        List<String> typeList;
        if ("1".equals(type)) {
            typeList = Arrays.asList(GetNewListTask.newTypes);
        } else if ("2".equals(type)) {
            typeList = Arrays.asList(GetVideoListTask.videoTypes);
        } else {
            typeList = new ArrayList<>();
            typeList.add("wuhan");
        }
        for (String newType : typeList) {
            MenuBean menuBean = new MenuBean();
            menuBean.setTitle(newType);
            menuBean.setType(type);
            menuBean.setNewsType(newType);
            menuBeans.add(menuBean);
        }

        return menuBeans;
    }


}
