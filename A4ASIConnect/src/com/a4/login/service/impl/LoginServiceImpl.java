package com.a4.login.service.impl;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.a4.login.beans.AccessBean;
import com.a4.login.beans.Login;
import com.a4.loginservice.LoginService;

public class LoginServiceImpl implements LoginService {

	private Logger _LOGGER = Logger.getLogger(getClass());

	AccessBean accessBean = null;
	private RestTemplate restTemplateClass;

	private String loginApiURL;

	@Override
	public AccessBean doLogin(Login login) {
		//TESTING COMMIT
		return null;
	}

	public RestTemplate getRestTemplateClass() {
		return restTemplateClass;
	}

	public void setRestTemplateClass(RestTemplate restTemplateClass) {
		this.restTemplateClass = restTemplateClass;
	}

	public String getLoginApiURL() {
		return loginApiURL;
	}

	public void setLoginApiURL(String loginApiURL) {
		this.loginApiURL = loginApiURL;
	}

}
