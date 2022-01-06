package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OrganizationTest {
	
	@Test
	void check() {
		assertTrue(ModelChecker.checkClass(Organization.class));
	}

}
