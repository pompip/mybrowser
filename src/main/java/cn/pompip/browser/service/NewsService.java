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
import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class NewsService {

    @Autowired
    private NewDao newDao;

    @Autowired
    private NewsContentDao newsContentDao;


    public NewsBean getById(long id) {
        return newDao.getOne(id);
    }


    public List<NewsBean> pageList(NewsBean t, int pageNum, int pageSize) {
        return newDao.findAll(Example.of(t), PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "publishTime")).getContent();
    }


    public void insertNewsBatch(List<NewsBean> list) {
        for (NewsBean newsBean : list) {
            if (!newDao.existsByUrlmd5(newsBean.getUrlmd5())) {
                newDao.save(newsBean);
            }
        }
    }


    public void insertVideoBatch(List<NewsBean> list) {
        newDao.saveAll(list);
    }

    //
    @Transactional
    public void insertNews(NewsBean newsBean, NewsContentBean newsContentBean) {
        NewsBean news = newDao.save(newsBean);
        newsContentBean.setNewsId(news.getId());
        newsContentDao.save(newsContentBean);
    }

    public NewsContentBean findNewsContentByID(Long id) throws Throwable {
        NewsContentBean bean = newsContentDao.findNewsContentBeanByNewsId(id);
        if (bean == null) {
            throw new NoResultException("没有找到新闻");
        } else {
            return bean;
        }
//        return bean.orElseThrow((Supplier<Throwable>) () -> new NoResultException("没有找到新闻"));
    }

}
