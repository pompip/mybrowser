package cn.pompip.browser.dao;

import cn.pompip.browser.model.CommentPraiseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPraiseDao extends JpaRepository<CommentPraiseBean,Long> {

}
