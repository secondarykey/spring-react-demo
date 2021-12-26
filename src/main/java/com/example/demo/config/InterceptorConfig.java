package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.controller.AuthenticationInterceptor;
import com.example.demo.controller.SessionInterceptor;

@Configuration
@ConditionalOnWebApplication
public class InterceptorConfig implements WebMvcConfigurer {

	private static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry ) {

		logger.debug("addInterceptors()");

		//TODO 排他制御するURLの統一
		List<String> noneAuthURLs = new ArrayList<>();
		noneAuthURLs.add("/api/v1/login");
		noneAuthURLs.add("/api/v1/password");

		registry.addInterceptor(new AuthenticationInterceptor()).excludePathPatterns(noneAuthURLs);
		registry.addInterceptor(new SessionInterceptor());
	}
}
