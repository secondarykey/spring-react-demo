package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Operation;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
public class OperationRepositoryTest {
	
	@Autowired
	OperationQueryRepository repo;
	
	@Test
	void testFindBelong() {
		Date date = DateUtil.parseDate("2022-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		List<Operation> opes = repo.find(1, cal.getTime(),"ja");
		assertEquals(opes.size(),2,"適用でOperationが取得できること");
		
		Operation ope = opes.get(0);
		assertNotNull(ope.getName(),"名称が取れていること");
		ope = opes.get(1);
		assertNull(ope.getName(),"名称が取れないこと");
		
		cal.add(Calendar.MONTH, 1);
		opes = repo.find(1, cal.getTime(),"ja");
		assertEquals(opes.size(),0,"日付変更で０件になること");

		opes = repo.find(4, cal.getTime(),"ja");
		assertEquals(opes.size(),3,"日付変更で違う組織が3件になること");
	}

}
