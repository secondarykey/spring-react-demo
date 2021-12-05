package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TimeRepositoryTest {
	
	@Autowired
	TimeRepository repository;

	@Test
	void testInsertObject() {
		repository.insertObject();
	}
	@Test
	void testInsert() {
		repository.insert();
	}

	@Test
	void testInsertText() {
		repository.insertText();
	}

	@Test
	void testInsertOnlyTz() {
		repository.insertTextOnlyTZ();
	}
}
