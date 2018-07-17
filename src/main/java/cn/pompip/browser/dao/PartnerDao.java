package cn.pompip.browser.dao;

import cn.pompip.browser.model.PartnerBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerDao extends JpaRepository<PartnerBean,Long> {

}
