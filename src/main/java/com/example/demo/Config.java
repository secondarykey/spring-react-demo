package com.example.demo;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Config {
	@PostConstruct
	public void initializeTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
