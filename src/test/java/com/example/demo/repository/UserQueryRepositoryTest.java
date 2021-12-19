package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
public class UserQueryRepositoryTest {

	@Autowired
	UserQueryRepository query;

	@Test
	void testFindByPassword() {
		User user = query.findByPassword("user","passw0rd");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		user = query.findByPassword("user","password");
		assertNull(user);
	}

	@Test
	void testJoinRole() {
		User user = query.joinRole("user");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		//assertEquals(user.getRoleName(),"一般ユーザ");
	}
	
}
