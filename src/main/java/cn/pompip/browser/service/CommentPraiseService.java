package cn.pompip.browser.service;


import cn.pompip.browser.dao.CommentPraiseDao;
import cn.pompip.browser.model.CommentPraiseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CommentPraiseService {

    @Autowired
    private CommentPraiseDao dao;

    public int insert(CommentPraiseBean t) {
        dao.save(t);
        return 0;
    }

    public long count(CommentPraiseBean t) {
        return dao.count(Example.of(t));
    }

}
