package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class InterceptorConfig implements WebMvcConfigurer {

	private static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);
	
	@Autowired
	private SessionInterceptor session;
	@Autowired
	private AuthenticationInterceptor authentication;

	@Override
	public void addInterceptors(InterceptorRegistry registry ) {

		logger.debug("addInterceptors()");

		List<String> noneAuthURLs = new ArrayList<>();
		noneAuthURLs.add("/api/v1/login");
		noneAuthURLs.add("/api/v1/password");
		registry.addInterceptor(authentication).addPathPatterns("/api/**").excludePathPatterns(noneAuthURLs);

		registry.addInterceptor(session);
	}
}
