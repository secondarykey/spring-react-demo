package com.example.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class SessionInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Value("${application.default.language}")
    public String defaultLanguage;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String lang = request.getHeader("Language");
		if ( Util.isEmpty(lang) ) {
			lang = defaultLanguage;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("language", lang);

		return true;
	}
}
