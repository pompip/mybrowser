package cn.pompip.browser.dao;

import cn.pompip.browser.model.ConfigBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ConfigDao extends JpaRepository<ConfigBean,Long> {

	//通过类型查询浏览器配置信息
//	public ConfigBean selectByType(@Param("type") int type) throws SQLException;

	ConfigBean findConfigBeanByType(int type);

}
