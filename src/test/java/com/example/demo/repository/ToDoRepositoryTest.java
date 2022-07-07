package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.model.Todos;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public class ToDoRepositoryTest {
	
	@Autowired
	ToDoRepository todoRepository;

	@Test
	void testFind() {
		Iterable<Todos> itr = todoRepository.findAll();
		List<Todos> list = new ArrayList<>();
		itr.forEach(list::add);
		assertNotNull(list);
		assertEquals(list.size(),2);
	}

}
