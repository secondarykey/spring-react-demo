package com.example.demo.config;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Autowired
    HttpSession session;
	public Session() {
	}

	public String getLanguage() {
		Object sess = session.getAttribute("language");
		if ( sess == null ) {
			return "ja";
		}
		return (String)sess;
	}
	public void setLanguage(String language) {
		session.setAttribute("language", language);
	}
}
