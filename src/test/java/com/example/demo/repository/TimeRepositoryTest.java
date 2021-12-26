package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Time;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
public class TimeRepositoryTest {
	
	@Autowired
	TimeRepository repository;

	@Test
	void testInsertText() {
		Time time = new Time();
	
		Date date = DateUtil.parse("2020-10-11 12:13:14");
		OffsetDateTime zone = DateUtil.zone(date, "JST");

		time.setValue(zone.toString());
		time.setDate(date);
		time.setTime(date);
		time.setDateToWithout(date);
		time.setOffsetToWith(zone);
		
		repository.save(time);
	
		Iterable<Time> list = repository.findAll();
		List<Time> times = new ArrayList<>();
		list.forEach(times::add);
		
		time = times.get(0);
		assertEquals(times.size(),1);
	}

}
