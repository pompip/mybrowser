package cn.pompip.browser.dao;

import cn.pompip.browser.model.NewsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewDao extends JpaRepository<NewsBean,Long> {

    boolean existsByUrlmd5(String urlmd5);


    boolean existsByNewsType(String newsType);
}
