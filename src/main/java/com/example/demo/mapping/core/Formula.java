package com.example.demo.mapping.core;

import com.example.demo.util.Util;

/**
 * 論理式
 * @author secon
 */
public class Formula implements Expression {

	/**
	 * 論理式
	 * @author secon
	 */
	private enum Operator {
		AND(" AND "), 
		OR(" OR "),
		NOT("NOT");
		private String v;
		Operator(String v) {
			this.v = v;
		}
		public String value() {
			return v;
		}
	}

	private Expression left;
	private Expression right;
	private Operator op;

	public static Expression and(Expression left,Expression right) {
		Formula fml = new Formula();
		fml.left = left;
		fml.right = right;
		fml.op = Operator.AND;
		return fml;
	}

	public static Expression or(Expression left,Expression right) {
		Formula fml = new Formula();
		fml.left = left;
		fml.right = right;
		fml.op = Operator.OR;
		return fml;
	}

	public static Expression not(Expression exp) {
		Formula fml = new Formula();
		fml.right = exp;
		fml.op = Operator.NOT;
		return fml;
	}

	@Override
	public String toSQL() {
		if ( this.op != Operator.NOT ) {
			return String.format("(%s%s%s)", this.left.toSQL(),this.op.value(),this.right.toSQL());
		}
		return String.format("%s%s", this.op,this.right.toSQL());
	}

	@Override
	public Expression and(Expression exp) {
		return and(this,exp);
	}

	@Override
	public Expression or(Expression exp) {
		return or(this,exp);
	}

	@Override
	public Expression not() {
		return not(this);
	}

	@Override
	public Object[] values() {
		Object[] lv = null;
		if ( left != null ) {
			lv = left.values();
		}

		Object[] rv = null;
		if ( right != null ) {
			rv = right.values();
		}

		if ( lv != null && rv != null ) {
			return Util.newArray(lv,rv);
		}
		
		if ( lv != null ) {
			return lv;
		}
		if ( rv != null ) {
			return rv;
		}
		return null;
	}
}
