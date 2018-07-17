package cn.pompip.browser.dao;

import cn.pompip.browser.model.MenuBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<MenuBean,Long> {
	
}
