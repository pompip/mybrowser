package cn.pompip.browser.service;


import cn.pompip.browser.dao.ConfigDao;
import cn.pompip.browser.model.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConfigService  {

	@Autowired
	private ConfigDao dao;

	public ConfigBean selectByType(int type)  {
		ConfigBean config = this.dao.findConfigBeanByType(type);
		return config;
	}


}
