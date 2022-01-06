package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.transfer.response.OperationResponse;
import com.example.demo.transfer.response.WorkerResponse;

@SpringBootTest(classes = DemoApplication.class)
public class WorkerServiceTest {

	@Autowired
	WorkerService service;

	@Test
	void testFind() {
		WorkerResponse res = service.find(1, "2022-01-01", "ja");
		assertNotNull(res);
		List<OperationResponse> list = res.getOperationList();
		assertNotNull(list);
		assertEquals(list.size(),2);
		
	}
}
