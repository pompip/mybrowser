package cn.pompip.browser.dao;

import cn.pompip.browser.model.NewCommentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewCommentDao extends JpaRepository<NewCommentBean, Long> {


}
