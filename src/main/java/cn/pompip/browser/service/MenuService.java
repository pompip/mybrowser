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
    private MenuDao menuDao;


    public List<MenuBean> queryAllMenu(String type) {
//        List<MenuBean> menuBeans = menuDao.findAllByTypeOrderBySort(type);
        if ("1".equals(type)){
            return menuDao.findExitNewsMenuNative();
        }else {
            return menuDao.findExitVideoMenuNative();
        }

    }


}
