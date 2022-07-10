package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("TODOS")
public class ToDo implements Model {

	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;

	@Column("VALUE")
	@MappingRS("value")
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
	public void setValue(String value) {
		this.value = value;
	}
	
}
