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
		Object[] vals = e.values();
		assertEquals(vals.length,3);
		assertEquals(vals[0],"A");
		assertEquals(vals[1],"B");
		assertEquals(vals[2],"C");

		List<Integer> in = new ArrayList<>();
		in.add(1);
		in.add(2);
		e = Exp.in("I",in);
		assertEquals(e.toSQL(),"(\"I\" IN (?,?))","In Test List version");

		vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],1);
		assertEquals(vals[1],2);

	}

	@Test
	void testRelation()  {
		Expression e = Exp.eq("A",1).and(Exp.eq("B",2));	
		//AND でつなげる
		assertEquals(e.toSQL(),"((\"A\" = ?) AND (\"B\" = ?))","AND Test");
		Object[] vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],1);
		assertEquals(vals[1],2);

		//OR でつなげる
		e = Exp.eq("A",4).or(Exp.eq("B",5));	
		//AND でつなげる
		assertEquals(e.toSQL(),"((\"A\" = ?) OR (\"B\" = ?))","OR Test");
		vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],4);
		assertEquals(vals[1],5);	
		
		//NOTにする
		e = e.not();
		assertEquals(e.toSQL(),"NOT((\"A\" = ?) OR (\"B\" = ?))","OR+NOT Test");
		vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],4);
		assertEquals(vals[1],5);	

		//NOT 同士でつなげる
		e = Exp.eq("A",6).not().and(Exp.eq("B",7).not());
		assertEquals(e.toSQL(),"(NOT(\"A\" = ?) AND NOT(\"B\" = ?))","NOT AND Test");
		vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],6);
		assertEquals(vals[1],7);	
		//ここまで複雑であればわかりやすくNotしたものを２つ作ってANDをするべきとは思う
		Expression ea = Exp.eq("A",8).not();
		Expression eb = Exp.eq("B",9).not();
		e = ea.and(eb);
		assertEquals(e.toSQL(),"(NOT(\"A\" = ?) AND NOT(\"B\" = ?))","NOT AND Test");
		vals = e.values();
		assertEquals(vals.length,2);
		assertEquals(vals[0],8);
		assertEquals(vals[1],9);		
	}
	
	@Test
	void testEqName()  {
		Expression e = Exp.eqName("A","B");
		assertEquals(e.toSQL(),"(\"A\" = \"B\")","EqName Test");
	}
	
	
}
