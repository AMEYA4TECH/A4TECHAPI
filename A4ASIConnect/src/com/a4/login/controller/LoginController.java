package com.a4.login.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.a4.login.beans.AccessBean;
import com.a4.login.beans.Login;
import com.a4.loginservice.LoginService;





@RestController
@RequestMapping("login")
public class LoginController {
	private Logger              _LOGGER              = Logger.getLogger(getClass());
	
	@Autowired
    private LoginService  loginService;
	
	public LoginService getLoginService() {
		return loginService;
	}


	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}


	@RequestMapping(method = RequestMethod.POST, headers = "content-type=application/json, application/xml", produces = {
            "application/xml", "application/json" })
    public ResponseEntity<?> getAccessToken(HttpEntity<Login> requestEntity) throws Exception {
		_LOGGER.info("In Login Controller");
		AccessBean response = null;
        
        try {
        	_LOGGER.info("login processing");
            
            response = loginService.doLogin(requestEntity.getBody());
            if (response != null) {
            	_LOGGER.info("login auth token generated");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
         
        } catch (Exception e) {
        	_LOGGER.error("Error while doing Login: " +e.getMessage(),e);
        }
        return null;
    }

	
	
	

}
