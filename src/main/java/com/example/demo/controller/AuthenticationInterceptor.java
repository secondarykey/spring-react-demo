package com.example.demo.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.transfer.LoginUser;
import com.example.demo.util.DateUtil;
import com.example.demo.util.EncryptUtil;
import com.example.demo.util.Util;


public class AuthenticationInterceptor implements HandlerInterceptor {
	
	public static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if ( isAuth(request) ) {
			String authHeader = request.getHeader("Authorization");
			if ( Util.isEmpty(authHeader) ) {
				//認証エラー
				return false;
			}
			try {
				LoginUser user = EncryptUtil.decodeUser(authHeader);
				Date expiry = DateUtil.parse(user.getExpiry());
				Date now = new Date();
				if ( expiry.before(now) ) {
					//有効期限切れエラーへ
					return false;
				}
			} catch ( Exception ex ) {
				logger.error("APIの認証データ復号化に失敗",ex);
				return false;
			}
		}

		return true;
	}

	/**
	 * 認証ヘッダが必要か
	 * @param request
	 * @return true = 必要
	 */
	private boolean isAuth(HttpServletRequest request) {
		return true;
	}
}
