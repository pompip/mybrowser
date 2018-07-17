package cn.pompip.browser.service;

import cn.pompip.browser.dao.NewCommentDao;
import cn.pompip.browser.model.NewCommentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCommentService  {

    @Autowired
    private NewCommentDao dao;

    public int insert(NewCommentBean t) {
        dao.save(t);
        return 0;
    }


    public NewCommentBean getById(long id) {
        return dao.getOne(id);
    }



    public List<NewCommentBean> pageList(NewCommentBean t, int pageNum, int pageSize) {

        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return dao.findAll(Example.of(t), pageable).getContent();
    }

    public int praise(NewCommentBean t) {
        return (int) dao.count(Example.of(t));
    }


}
