package com.example.demo.mapping.core;

public class Order {

	private enum Direction {
		ASC,DESC
	}
	
	public static Order createAsc(String col) {
		return new Order(col,Direction.ASC);
	}

	public static Order createDesc(String col) {
		return new Order(col,Direction.DESC);
	}

	private String name;
	private Direction dir;
	private Order front;
	private Order rear;

	private Order(String name,Direction dir) {
		this.name = name;
		this.dir = dir;
	}

	public Order asc(String name) {
		this.rear = Order.createAsc(name);
		this.rear.front = this;
		return this.rear;
	}

	public Order desc(String name) {
		this.rear = Order.createDesc(name);
		this.rear.front = this;
		return this.rear;
	}
	
	public String toSQL() {
		Order first = getFirst();
		StringBuffer buf = new StringBuffer();
		
		Order target = first;
		while ( true ) {
			String part = target.toString();
			if ( buf.length() != 0 ) {
				buf.append(",");
			}
			buf.append(part);

			if ( target.rear == null ) {
				break;
			}
			target = target.rear;
		}
		return buf.toString();
	}

	private Order getFirst() {
		if ( this.front != null ) {
			return this.front.getFirst();
		} else {
			return this;
		}
	}
	
	public String toString() {
		return name + " " + dir.toString();
	}
}
