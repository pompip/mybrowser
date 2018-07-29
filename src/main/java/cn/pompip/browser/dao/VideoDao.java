package cn.pompip.browser.dao;

import cn.pompip.browser.model.VideoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDao extends JpaRepository<VideoBean,Long> {
    boolean existsByUrlmd5(String urlmd5);
}
