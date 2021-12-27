package com.example.demo.transfer.response;

import java.io.Serializable;

import com.example.demo.transfer.LoginUser;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private LoginUser user;
	
	private String language;

	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
