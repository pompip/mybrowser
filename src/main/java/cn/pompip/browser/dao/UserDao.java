package cn.pompip.browser.dao;

import cn.pompip.browser.model.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserBean,Long> {

}
