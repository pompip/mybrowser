package cn.pompip.browser.service;


import cn.pompip.browser.dao.PartnerDao;
import cn.pompip.browser.model.PartnerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService  {

    @Autowired
    private PartnerDao dao;

    public List<PartnerBean> queryList(PartnerBean t) {
        return dao.findAll(Example.of(t));
    }


}
