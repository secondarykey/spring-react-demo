package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	void testCapitalize() {
		assertEquals(Util.capitalize(null),"");
		assertEquals(Util.capitalize(""),"");
		assertEquals(Util.capitalize("a"),"A");
		assertEquals(Util.capitalize("A"),"A");
		assertEquals(Util.capitalize("oomoji"),"Oomoji");
		assertEquals(Util.capitalize("AAAA"),"AAAA");
	}

	@Test
	void testIsEmptyString() {
	}

	@Test
	void testIsEmptyList() {
	}

	@Test
	void testIsEmptyArray() {
	}

	@Test
	void testCount() {
	}

	@Test
	void testEquals() {
	}
}
