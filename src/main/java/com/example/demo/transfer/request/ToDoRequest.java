package com.example.demo.transfer.request;

public class ToDoRequest extends Arguments {
	private int id;
	private String value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setPassword(String value) {
		this.value = value;
	}
}
