package com.example.demo.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.transfer.LoginUser;
import com.example.demo.util.DateUtil;
import com.example.demo.util.EncryptUtil;
import com.example.demo.util.Util;

@Component
@ConditionalOnWebApplication
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	private static String[] targetURLPrefix = {"/api"};
	
	@Autowired
	Session session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if ( !isTarget(request) ) {
			logger.debug("対象URLでないのでOK:{}",request.getRequestURL());
			return true;
		}

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

		return true;
	}

	/**
	 * 認証ヘッダが必要か
	 * @param request
	 * @return true = 必要
	 */
	private boolean isTarget(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		String pure = uri.replaceAll(path, "");

		logger.info("request:"+pure);

		for ( String target : targetURLPrefix ) {
			if ( target.indexOf(pure) == 0 ) {
				return true;
			}
		}
		return false;
	}

}
