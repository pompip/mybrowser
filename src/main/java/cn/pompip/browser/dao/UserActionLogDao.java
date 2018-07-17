package cn.pompip.browser.dao;

import cn.pompip.browser.model.UserActionLogBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionLogDao extends JpaRepository<UserActionLogBean,Long> {

}
