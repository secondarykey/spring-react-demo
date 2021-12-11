package com.example.demo.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.transfer.response.LoginUser;
import com.example.demo.util.DateUtil;
import com.example.demo.util.EncryptUtil;
import com.example.demo.util.Util;


public class AuthenticationInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	private static String[] targetURLPrefix = {"/api"};
	private static String[] ignoreURLs = {"/api/v1/login","/api/v1/password"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if ( isAuth(request) ) {

			String authHeader = request.getHeader("Authorization");
			logger.info(authHeader);

			if ( Util.isEmpty(authHeader) ) {
				return false;
			}
			try {
				LoginUser user = EncryptUtil.decodeUser(authHeader);
				Date expiry = DateUtil.parse(user.getExpiry());
				Date now = new Date();
				if ( expiry.before(now) ) {
					return false;
				}
			} catch ( Exception ex ) {
				logger.error("APIの認証データ復号化に失敗",ex);
				return false;
			}
		}

		return true;
	}

	private boolean isAuth(HttpServletRequest request) {

		String uri = request.getRequestURI();
		String path = request.getContextPath();
		String pure = uri.replaceAll(path, "");
		logger.info("request:"+pure);
		for ( String ignore : targetURLPrefix ) {
			if ( pure.indexOf(ignore) == 0 ) {
				if ( !ignoreURL(pure) ) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 排他URL判定
	 * @param url 対象URL
	 * @return 
	 */
	private boolean ignoreURL(String url) {

		for ( String ignore : ignoreURLs ) {
			if ( url.equals(ignore) ) {
				return false;
			}
		}
		return true;
	}
}
