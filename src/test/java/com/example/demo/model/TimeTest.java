package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimeTest {
	
	@Test
	void check() {
		assertTrue(ModelChecker.checkClass(Times.class));
	}

}
