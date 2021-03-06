package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FileUtilTest {
	
	@Test
	void testJoin() {
		String got = FileUtil.join("test","join");
		assertEquals(got,"test\\join");

		got = FileUtil.join();
		assertEquals(got,"");
		
		got = FileUtil.join("test","join/test");
		assertEquals(got,"test\\join\\test");
	}
	
	@Test
	void testGetResource() {
		String sql = FileUtil.getResource("/sql/test.sql");
		assertEquals(sql,"SELECT * FROM TEST","リソースからの読み込みテスト");
	}

}
