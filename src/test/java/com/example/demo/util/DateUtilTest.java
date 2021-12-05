package com.example.demo.util;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	void testSql() {
		DateUtil.sql(new Date(), "PST");
	}

}
