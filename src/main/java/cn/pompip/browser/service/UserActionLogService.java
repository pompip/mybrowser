package cn.pompip.browser.service;

import cn.pompip.browser.dao.UserActionLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActionLogService  {

    @Autowired
    private UserActionLogDao dao;



}
