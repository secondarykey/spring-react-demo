package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@ConditionalOnWebApplication
public class SessionInterceptor implements HandlerInterceptor {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Autowired
	private Session session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("preHandle()");
		//ヘッダから言語を取得し、デフォルト時にデフォルトの言語を指定する
		String lang = request.getHeader("Language");
		session.setLanguage(lang);
		return true;
	}
}
