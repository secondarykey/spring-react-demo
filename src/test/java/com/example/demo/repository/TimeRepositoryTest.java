package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.model.Times;
import com.example.demo.util.DateUtil;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public class TimeRepositoryTest {
	
	@Autowired
	TimeRepository repository;

	@Test
	void testInsertTime() {
		Times time = new Times();
	
		Date date = DateUtil.parse("2020-10-11 12:13:14");
		OffsetDateTime zone = DateUtil.zone(date, "JST");

		time.setValue(zone.toString());
		time.setDate(date);
		time.setTime(date);
		time.setDateWithout(date);
		time.setOffsetWith(zone);
		time.setRegister(true);
		
		repository.save(time);
	
		Iterable<Times> list = repository.findAll();
		List<Times> times = new ArrayList<>();
		list.forEach(times::add);
		
		time = times.get(0);
		assertEquals(times.size(),1);
	}

}
