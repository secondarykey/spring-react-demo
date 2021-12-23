package com.example.demo.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class Timezone {
	@PostConstruct
	public void initializeTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
