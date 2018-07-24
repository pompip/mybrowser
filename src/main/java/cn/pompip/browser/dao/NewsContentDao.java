package cn.pompip.browser.dao;

import cn.pompip.browser.model.NewsContentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsContentDao extends JpaRepository<NewsContentBean,Long> {

    NewsContentBean findNewsContentBeanByNewsId(Long id) throws Exception;




}
