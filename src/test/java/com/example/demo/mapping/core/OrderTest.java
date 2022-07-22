package com.example.demo.mapping.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderTest {

	@Test
	void testCreate() {
		Order o = Order.createAsc("A");
		assertEquals(o.toString(),"\"A\" ASC");
		assertEquals(o.toSQL(),"\"A\" ASC");

		o = Order.createDesc("D");
		assertEquals(o.toString(),"\"D\" DESC");
		assertEquals(o.toSQL(),"\"D\" DESC");
		
		o = Order.createAsc("\"A\"");
		assertEquals(o.toString(),"\"A\" ASC");
		assertEquals(o.toSQL(),"\"A\" ASC");

		o = Order.createAsc("\"T\".\"A\"");
		assertEquals(o.toString(),"\"T\".\"A\" ASC");
		assertEquals(o.toSQL(),"\"T\".\"A\" ASC");
	}

	@Test
	void testRelation() {
		Order o = Order.createAsc("A").desc("D").asc("A2").desc("D2");
		assertEquals(o.toSQL(),"\"A\" ASC,\"D\" DESC,\"A2\" ASC,\"D2\" DESC");
	}
}
