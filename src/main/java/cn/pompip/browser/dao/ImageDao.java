package cn.pompip.browser.dao;


import cn.pompip.browser.model.ImageBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDao extends JpaRepository<ImageBean,Long> {
}
