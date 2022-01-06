package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Day;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
public class DayRepositoryTest {
	
	@Autowired
	DayQueryRepository repo;
	
	@Test
	void testFindBelong() {
		Date date = DateUtil.parseClient("2022-01-01 00:00");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		List<Day> days = repo.findByBelong(1, cal.getTime());
		assertEquals(days.size(),4,"他の組織の休日が取れてないこと");

		days = repo.findByBelong(2, cal.getTime());
		assertEquals(days.size(),1,"組織変更で別の組織が取れる");

		cal.add(Calendar.MONTH, 1);
		days = repo.findByBelong(1, cal.getTime());
		assertEquals(days.size(),4,"１ヶ月前でも取得できていること");
		
		cal.add(Calendar.MONTH, 1);
		days = repo.findByBelong(1, cal.getTime());
		assertEquals(days.size(),3,"12月データが対象から外れること");
		
		
	}

}
