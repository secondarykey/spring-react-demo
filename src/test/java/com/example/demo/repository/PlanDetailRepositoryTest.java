package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public class PlanDetailRepositoryTest {

	@Autowired
	PlanDetailRepository repo;

	@Test
	void testFind() {

		
		
	}

}
