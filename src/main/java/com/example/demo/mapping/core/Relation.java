package com.example.demo.mapping.core;

/**
 * リレーション用の条件式
 * @author secon
 *
 */
public class Relation {

	private Join join;
	private QuerySet qs;
	private Expression ev;

	public Relation(Join join,QuerySet qs, Expression ev) {
		this.join = join;
		this.qs = qs;
		this.ev = ev;
	}

	public QuerySet getQs() {
		return qs;
	}

	public Expression getExpression() {
		return ev;
	}

	public Join getJoin() {
		return join;
	}
}
