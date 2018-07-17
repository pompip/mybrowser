package cn.pompip.browser.service;


import cn.pompip.browser.dao.MenuDao;
import cn.pompip.browser.model.MenuBean;
import cn.pompip.browser.task.GetNewListTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService  {

    @Autowired
    private MenuDao dao;


    public List<MenuBean> queryAllMenu() {
        ArrayList<MenuBean> menuBeans = new ArrayList<>();
        for (int i =1;i<=GetNewListTask.newTypes.length;i++){
            MenuBean menuBean = new MenuBean();
            menuBean.setStatus(0);
            menuBean.setTitle("视频");
            menuBean.setType(i);
        }


//        MenuBean menuBean1 = new MenuBean();
//        menuBean1.setTitle("新闻");
//        menuBean1.setType(1);

//
//        menuBeans.add(menuBean1);
//        menuBeans.add(menuBean);
        return menuBeans;
    }


}
