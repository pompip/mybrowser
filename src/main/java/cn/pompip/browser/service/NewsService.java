package cn.pompip.browser.service;


import cn.pompip.browser.dao.NewDao;
import cn.pompip.browser.dao.NewsContentDao;
import cn.pompip.browser.exception.NoResultException;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewDao newDao;

    @Autowired
    private NewsContentDao newsContentDao;


    public List<NewsBean> pageList(String newsType, int pageNum, int pageSize) {

        NewsBean probe = new NewsBean();
        probe.setNewsType(newsType);

        return newDao.findAll(Example.of(probe), PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime")).getContent();
    }

    public boolean existNewsType(String newsType){
        return newDao.existsByNewsType(newsType);
    }


    @Transactional
    public void insertNews(NewsBean newsBean, NewsContentBean newsContentBean) {
        boolean exists = newDao.existsByUrlmd5(newsBean.getUrlmd5());
        if (exists) return;
        NewsBean news = newDao.save(newsBean);
        newsContentBean.setNewsId(news.getId());
        newsContentDao.save(newsContentBean);
    }

    public NewsContentBean findNewsContentByID(Long id) throws NoResultException {
        NewsContentBean bean = newsContentDao.findNewsContentBeanByNewsId(id);
        if (bean == null) {
            throw new NoResultException("没有找到新闻");
        } else {
            return bean;
        }
    }


}
