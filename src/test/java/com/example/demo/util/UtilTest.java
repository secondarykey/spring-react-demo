package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	void testCapitalize() {
		assertEquals(Util.capitalize(null,true),"");
		assertEquals(Util.capitalize(null,false),"");
		assertEquals(Util.capitalize("",true),"");
		assertEquals(Util.capitalize("",false),"");
		assertEquals(Util.capitalize("a",true),"A");
		assertEquals(Util.capitalize("a",false),"A");
		assertEquals(Util.capitalize("A",true),"A");
		assertEquals(Util.capitalize("A",false),"A");
		assertEquals(Util.capitalize("oomoji",true),"Oomoji");
		assertEquals(Util.capitalize("oomoji",false),"Oomoji");
		assertEquals(Util.capitalize("AAAA",true),"Aaaa");
		assertEquals(Util.capitalize("AAAA",false),"AAAA");
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
