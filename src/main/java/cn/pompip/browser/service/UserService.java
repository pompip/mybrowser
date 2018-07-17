package cn.pompip.browser.service;


import cn.pompip.browser.dao.UserDao;
import cn.pompip.browser.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    public int insert(UserBean t) {
        dao.save(t);
        return 0;
    }


    public int update(UserBean t) {
         dao.save(t);
        return 0;
    }


    public UserBean getById(long id) {
        return dao.getOne(id);
    }


    public List<UserBean> queryList(UserBean t) {
        return dao.findAll( Example.of(t));
    }





}
