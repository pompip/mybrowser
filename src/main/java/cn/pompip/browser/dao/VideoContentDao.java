package cn.pompip.browser.dao;

import cn.pompip.browser.model.VideoContentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoContentDao extends JpaRepository<VideoContentBean, Long> {

    VideoContentBean findVideoBeanByVideoId(String videoId);
}
