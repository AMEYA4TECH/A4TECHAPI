package com.a4.login.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.a4.util.*;
import com.a4.login.beans.AccessBean;
import com.a4.login.beans.Login;
import com.a4.loginservice.LoginService;

public class LoginServiceImpl implements LoginService {

	private Logger _LOGGER = Logger.getLogger(getClass());
	
	private static final Integer AUTH_TOKEN_EXPIRE_MINUTES = 65;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SZ";
    
	AccessBean accessBean = null;
	private RestTemplate restTemplateClass;

	private String loginApiURL;

	@Override
	public AccessBean doLogin(Login login) {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        AccessBean accessBean = null;
        try {
        	HttpHeaders header = new HttpHeaders();
            header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            header.setContentType(MediaType.APPLICATION_JSON);
          
            
            Map<String, String> body = new HashMap<String, String>();     
            
            body.put(ApplicationConstants.CONST_ASI_NUMBER, login.getAsi_number());
            body.put(ApplicationConstants.CONST_USERNAME,login.getUsername());
            body.put(ApplicationConstants.CONST_PASSWORD, login.getPassword());

            
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, header);
            _LOGGER.info("Hitting ASI endpoint for Login");
            ResponseEntity<AccessBean> response = restTemplateClass.exchange(loginApiURL, HttpMethod.POST, httpEntity, AccessBean.class);
            accessBean = new AccessBean();
            accessBean = response.getBody();
            _LOGGER.info("ASI response recieved");
            accessBean.setAccessToken(accessBean.getAccessToken());

            Calendar expireTimeCalculator = Calendar.getInstance();
            expireTimeCalculator.add(Calendar.MINUTE, AUTH_TOKEN_EXPIRE_MINUTES);
            
            DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
            accessBean.setTokenExpirationTime(dateFormatter.format(expireTimeCalculator.getTime()));
            
            
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return accessBean;
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
