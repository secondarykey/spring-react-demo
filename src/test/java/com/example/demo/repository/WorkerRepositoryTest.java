package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.model.Worker;
import com.example.demo.util.DateUtil;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public class WorkerRepositoryTest {
	
	@Autowired
	WorkerQueryRepository repo;
	
	@Test
	void testFindBelong() {
		Date date = DateUtil.parseDate("2022-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		List<Worker> works = repo.find(1, cal.getTime());
		assertEquals(works.size(),2,"適用でWorkerが取得できること");
	
		cal.add(Calendar.DAY_OF_MONTH, 1);
		works = repo.find(1, cal.getTime());
		assertEquals(works.size(),2,"次の日も取得できること");

		cal.add(Calendar.DAY_OF_MONTH, 4);
		works = repo.find(1, cal.getTime());
		assertEquals(works.size(),0,"1/6は取得できないこと");

		works = repo.find(4, cal.getTime());
		assertEquals(works.size(),3,"違う組織が取得できること");
		
	}

}
