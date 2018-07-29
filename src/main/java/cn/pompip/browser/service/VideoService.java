package cn.pompip.browser.service;

import cn.pompip.browser.dao.VideoContentDao;
import cn.pompip.browser.dao.VideoDao;
import cn.pompip.browser.exception.NoResultException;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.VideoBean;
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
public class VideoService {
    @Autowired
    private VideoContentDao videoContentDao;

    @Autowired
    private VideoDao videoDao;

    public List<VideoBean> pageList(String newsType, int pageNum, int pageSize) {
        VideoBean videoBean = new VideoBean();
        videoBean.setNewsType(newsType);
        return videoDao.findAll(Example.of(videoBean), PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "publishTime")).getContent();
    }

    @Transactional
    public void insertVideo(VideoBean newsBean, VideoContentBean videoContentBean) {

        if (!videoDao.existsByUrlmd5(newsBean.getUrlmd5())) {
            videoDao.save(newsBean);
        }

        videoContentDao.save(videoContentBean);

    }


    public VideoContentBean findVideoBeanById(Long id) throws NoResultException {
        VideoBean newsBean = videoDao.findById(id).orElseThrow(() -> new NoResultException("没有找到视频"));
        VideoContentBean videoContentBean = videoContentDao.findVideoBeanByVideoId(newsBean.getVideoId());
        if (videoContentBean == null) {
            VideoContentBean videoContentBean1 = getVideoListTask.parseVideo(newsBean);
            if (videoContentBean1 == null) {
                throw new NoResultException("没有找到视频");
            } else {
                return videoContentBean1;
            }
        } else {
            return videoContentBean;
        }
    }

    @Autowired
    GetVideoListTask getVideoListTask;
}
