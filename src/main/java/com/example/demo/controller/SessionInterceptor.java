package com.example.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@ConditionalOnWebApplication
public class SessionInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(true);
		Object lang = session.getAttribute("language");
		if ( lang == null ) {
			logger.info("デフォルトを設定");
			//TODO 実際はデフォルトの言語
			session.setAttribute("language", "en");
		}
		return true;
	}
}
