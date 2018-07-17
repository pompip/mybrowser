package cn.pompip.browser.dao;

import cn.pompip.browser.model.FeedbackBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDao extends JpaRepository<FeedbackBean,Long> {
	
}
