package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.example.demo.config.Session;
import com.example.demo.controller.AuthenticationInterceptor;

// TODO テスト時に同一アプリ内に存在するとなる
@SpringBootApplication
@ComponentScan(excludeFilters={
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value= Resource.class),
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value= Session.class),
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value= ExceptionHandler.class),
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value= AuthenticationInterceptor.class)})
public class BatchApplication {
	public static void main(String[] args) {
		System.exit(
			SpringApplication.exit(
				SpringApplication.run(BatchApplication.class, args)
			)
		);
	}
}
