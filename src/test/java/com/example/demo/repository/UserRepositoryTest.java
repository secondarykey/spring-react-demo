package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	UserRepository repo;

	@Test
	void testFind() {
		User user = repo.findById("user");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");
		//assertNull(user.getRoleName());

		user = repo.findById("admin");
		assertNotNull(user);
		assertEquals(user.getName(),"アドミン太郎");
	
		user = repo.findById("not found");
		assertNull(user);
	}

	@Test
	void testFindByPassword() {
		User user = repo.findByPassword("user","passw0rd");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		user = repo.findByPassword("user","password");
		assertNull(user);
	}

	@Test
	void testJoinRole() {
		User user = repo.joinRole("user");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		//assertEquals(user.getRoleName(),"一般ユーザ");
	}
	
	
	
	
	
}
