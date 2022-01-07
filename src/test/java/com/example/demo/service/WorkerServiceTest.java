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
		
		//TODO Workerはテストを後回し
		// データ構造があやしい
	
		/**
		OperationResponse opeRes = list.get(0);
		assertEquals(opeRes.getWorkers(),1);
		opeRes = list.get(1);
		assertEquals(opeRes.getWorkers(),1);

		res = service.find(4, "2022-01-07", "ja");
		list = res.getOperationList();
		assertNotNull(list);
		assertEquals(list.size(),3);
		opeRes = list.get(0);
		assertEquals(opeRes.getWorkers(),2,"同一作業に２人いること");
		opeRes = list.get(1);
		assertEquals(opeRes.getWorkers(),0,"いない作業が表現できること");
		opeRes = list.get(2);
		assertEquals(opeRes.getWorkers(),1,"１人の作業もデータ化されていること");
		*/

		res = service.find(1, "2021-12-31", "ja");
		assertNull(res,"作業が存在しない場合、Null");
	}
}
