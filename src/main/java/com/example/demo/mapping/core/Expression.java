package com.example.demo.mapping.core;

/**
 * Where 句用のインターフェース
 * @author secon
 */
public interface Expression {
	public String toSQL();
	public Expression and(Expression exp);
	public Expression or(Expression exp);
	public Expression not();
}
