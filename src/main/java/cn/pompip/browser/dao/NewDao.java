package cn.pompip.browser.dao;

import cn.pompip.browser.model.NewsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewDao extends JpaRepository<NewsBean,Long> {

//	public List<NewsBean> queryListByUrlMd5(Set<String> url);
//
//	public int insertNewsBatch(List<NewsBean> list);
//
//	public int insertVideoBatch(List<NewsBean> list);

//    void saveAndIgnore(NewsBean newBean);

//    List<NewsBean> findAllByUrlmd5(String urlmd5);
    boolean existsByUrlmd5(String urlmd5);

    List<NewsBean> findAllByNewsType(String newsType);

}
