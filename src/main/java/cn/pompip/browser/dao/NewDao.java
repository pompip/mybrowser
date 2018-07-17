package cn.pompip.browser.dao;

import cn.pompip.browser.model.NewBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewDao extends JpaRepository<NewBean,Long> {

//	public List<NewBean> queryListByUrlMd5(Set<String> url);
//
//	public int insertNewsBatch(List<NewBean> list);
//
//	public int insertVideoBatch(List<NewBean> list);

//    void saveAndIgnore(NewBean newBean);

//    List<NewBean> findAllByUrlmd5(String urlmd5);
    boolean existsByUrlmd5(String urlmd5);

}
