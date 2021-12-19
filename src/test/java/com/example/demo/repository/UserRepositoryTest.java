package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	UserRepository crud;

	@Test
	void testFind() {
		//TODO Crudのfind系は問題が起こる可能性が高い
	}

	
	
	
	
}
