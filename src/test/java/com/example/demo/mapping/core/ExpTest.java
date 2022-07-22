package com.example.demo.mapping.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ExpTest {

	@Test
	void testOperation() {
		Expression e = Exp.eq("A",1);

		assertEquals(e.toSQL(),"(\"A\" = ?)","EQ Test");
		e = Exp.lt("B",1);
		assertEquals(e.toSQL(),"(\"B\" < ?)","LT Test");
		e = Exp.le("C",1);
		assertEquals(e.toSQL(),"(\"C\" <= ?)","LE Test");
		e = Exp.gt("D",1);
		assertEquals(e.toSQL(),"(\"D\" > ?)","GT Test");
		e = Exp.ge("E",1);
		assertEquals(e.toSQL(),"(\"E\" >= ?)","GE Test");

		e = Exp.nullp("F");
		assertEquals(e.toSQL(),"(\"F\" IS NULL)","IsNull Test");
		e = Exp.notNull("G");
		assertEquals(e.toSQL(),"(\"G\" IS NOT NULL)","Is Not Null Test");

		e = Exp.in("H","A","B","C");
		assertEquals(e.toSQL(),"(\"H\" IN (?,?,?))","In Test");
		
		List<Integer> in = new ArrayList<>();
		in.add(1);
		in.add(2);
		e = Exp.in("I",in);
		assertEquals(e.toSQL(),"(\"I\" IN (?,?))","In Test List version");


	}
	@Test
	void testValue()  {
		//値設定を
	}
	
	@Test
	void testRelation()  {
		Expression e = Exp.eq("A",1).and(Exp.eq("B",1));	
		//AND でつなげる
		assertEquals(e.toSQL(),"((\"A\" = ?) AND (\"B\" = ?))","AND Test");
		//OR でつなげる
		e = Exp.eq("A",1).or(Exp.eq("B",1));	
		//AND でつなげる
		assertEquals(e.toSQL(),"((\"A\" = ?) OR (\"B\" = ?))","OR Test");
		//NOTにする
		e = e.not();
		assertEquals(e.toSQL(),"NOT((\"A\" = ?) OR (\"B\" = ?))","OR+NOT Test");

		//NOT 同士でつなげる
		e = Exp.eq("A",1).not().and(Exp.eq("B",1).not());
		assertEquals(e.toSQL(),"(NOT(\"A\" = ?) AND NOT(\"B\" = ?))","NOT AND Test");

		//ここまで複雑であればわかりやすくNotしたものを２つ作ってANDをするべきとは思う
		Expression ea = Exp.eq("A",1).not();
		Expression eb = Exp.eq("B",1).not();
		e = ea.and(eb);
		assertEquals(e.toSQL(),"(NOT(\"A\" = ?) AND NOT(\"B\" = ?))","NOT AND Test");
		
	}
	
	@Test
	void testEqName()  {
		Exp e = Exp.eqName("A","B");
		assertEquals(e.toSQL(),"(\"A\" = \"B\")","EqName Test");
	}
	
	
}
