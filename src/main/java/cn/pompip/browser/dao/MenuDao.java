package cn.pompip.browser.dao;

import cn.pompip.browser.model.MenuBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<MenuBean,Long> {

    List<MenuBean> findAllByTypeOrderBySort(String type);

    @Query(value = "select * from menu_bean where  news_type in (select distinct news_type from new_bean)    order by sort",nativeQuery = true)
    List<MenuBean> findExitNewsMenuNative();

    @Query(value = "select * from menu_bean where  news_type in (select distinct news_type from video_bean)    order by sort",nativeQuery = true)
    List<MenuBean> findExitVideoMenuNative();

	
}
