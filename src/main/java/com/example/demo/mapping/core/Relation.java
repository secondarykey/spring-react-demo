package com.example.demo.mapping.core;

/**
 * リレーション用の条件式
 * @author secon
 *
 */
public class Relation {

	private QuerySet qs;
	private Expression ev;

	public Relation(QuerySet qs, Expression ev) {
		this.qs = qs;
		this.ev = ev;
	}

	public QuerySet getQs() {
		return qs;
	}

	public Expression getExpression() {
		return ev;
	}
}
