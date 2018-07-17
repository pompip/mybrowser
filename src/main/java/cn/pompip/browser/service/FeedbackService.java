package cn.pompip.browser.service;


import cn.pompip.browser.dao.FeedbackDao;
import cn.pompip.browser.model.FeedbackBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService  {

    @Autowired
    private FeedbackDao dao;

    public int insert(FeedbackBean t) {
         dao.save(t);
        return 0;
    }


}
