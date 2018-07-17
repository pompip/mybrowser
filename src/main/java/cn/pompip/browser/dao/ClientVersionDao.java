package cn.pompip.browser.dao;

import cn.pompip.browser.model.ClientVersionBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientVersionDao extends JpaRepository<ClientVersionBean,Long> {

	//通过类型查询版本信息
//	public ClientVersionBean selectByType(int type) throws SQLException;

	ClientVersionBean findClientVersionBeanByType(int type);
	
}
