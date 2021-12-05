package com.example.demo.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.ToDo;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ToDoRepositoryTest {
	
	@Autowired
	ToDoRepository todoRepository;

	@Test
	void testFind() {
		List<ToDo> list = todoRepository.find();
		assertNotNull(list);
		assertEquals(list.size(),2);
	}

}
