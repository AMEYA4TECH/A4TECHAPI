package com.a4.loginservice;


import com.a4.login.beans.AccessBean;
import com.a4.login.beans.Login;

public interface LoginService {
public AccessBean doLogin(Login login);
}