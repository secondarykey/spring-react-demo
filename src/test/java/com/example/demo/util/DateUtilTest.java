package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

	@Test
	void testParse() {
		String buf = "2021-10-10 20:20:01";
		Date date = DateUtil.parse(buf);
		assertNotNull(date);
		assertEquals(date.getTime(),1633864801000L);
		
		assertThrows(RuntimeException.class,()-> DateUtil.parse("2021/10/10 20:20:01"));
	}

	@Test
	void testZone() {
		String buf = "2021-10-10 20:20:00";
		String fmt = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat fm1 = new SimpleDateFormat(fmt);
		DateTimeFormatter fm2 = DateTimeFormatter.ofPattern(fmt);
		try {
			Date date = fm1.parse(buf);
			OffsetDateTime zone = DateUtil.zone(date, "PST");
			String got = zone.format(fm2);

			assertEquals(buf,got);

		} catch (ParseException e) {
			fail(e);
		}

	}

}
