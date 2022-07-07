package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
	
	@Test
	void check() {
		assertTrue(ModelChecker.checkClass(Users.class));
	}

}
