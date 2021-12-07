package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
public class UserJoinRepositoryTest {
	@Autowired
	UserJoinRepository repo;

	@Test
	void testFind() {
		User user = repo.findByOther("id");
		assertNotNull(user);
	}

	@Test
	void testFindByName() {
		User user = repo.findByName("山田太郎");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");
		assertNotNull(user.getRoleObj());
	}

	
	
	
}
