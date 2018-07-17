package cn.pompip.browser.service;


import cn.pompip.browser.dao.ClientVersionDao;
import cn.pompip.browser.model.ClientVersionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientVersionService {

	@Autowired
	private ClientVersionDao dao;

	public ClientVersionBean selectByType(int type)  {
		ClientVersionBean version = this.dao.findClientVersionBeanByType(type);
		return version;
	}

}
