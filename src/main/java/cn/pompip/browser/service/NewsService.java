package cn.pompip.browser.service;


import cn.pompip.browser.dao.NewDao;
import cn.pompip.browser.dao.NewsContentDao;
import cn.pompip.browser.dao.VideoContentDao;
import cn.pompip.browser.exception.NoResultException;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.model.VideoContentBean;
import cn.pompip.browser.task.GetVideoListTask;
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

    @Autowired
    private VideoContentDao videoContentDao;


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

    public NewsContentBean findNewsContentByID(Long id) throws NoResultException {
        NewsContentBean bean = newsContentDao.findNewsContentBeanByNewsId(id);
        if (bean == null) {
            throw new NoResultException("没有找到新闻");
        } else {
            return bean;
        }
    }

    @Transactional
    public void insertVideo(NewsBean newsBean, VideoContentBean videoContentBean) {

        if (!newDao.existsByUrlmd5(newsBean.getUrlmd5())) {
            newDao.save(newsBean);
        }

        videoContentDao.save(videoContentBean);

    }


    public VideoContentBean findVideoBeanById(Long id) throws NoResultException {
        NewsBean newsBean = newDao.findById(id).orElseThrow(() -> new NoResultException("没有找到视频"));
        VideoContentBean videoContentBean = videoContentDao.findVideoBeanByVideoId(newsBean.getVideoId());
        if (videoContentBean == null) {
            VideoContentBean videoContentBean1 = getVideoListTask.parseVideo(newsBean);
            if (videoContentBean1 ==null){
                throw new NoResultException("没有找到视频");
            }else {
                return videoContentBean1;
            }
        } else {
            return videoContentBean;
        }
    }

    @Autowired
    GetVideoListTask getVideoListTask;

}
