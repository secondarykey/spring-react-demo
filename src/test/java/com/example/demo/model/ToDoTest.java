package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ToDoTest {
	
	@Test
	void check() {
		assertTrue(ModelChecker.checkClass(Todos.class));
	}

}
