package com.example.demo.mapping.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.demo.model.Day;
import com.example.demo.util.FileUtil;

class QuerySetTest {

	@Test
	void testToSQL() {
		QuerySet set = QuerySet.create(Day.class, "", "");
		String sql = FileUtil.getResource("/sql/Day.sql");
		assertEquals(set.toSQL(),sql,"SQL Test");
		set.setWhere(Exp.eq(Day.ID, 2).and(Exp.eq(Day.VALUE,"test")));

		sql = FileUtil.getResource("/sql/DayWhere.sql");
		assertEquals(set.toSQL(),sql,"SQL Where Test");
		
		set.setOrder(Order.createAsc("ID").desc("VALUE"));
		sql = FileUtil.getResource("/sql/DayOrder.sql");
		assertEquals(set.toSQL(),sql,"SQL Order Test");
	}
	
}
