package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.anotation.model.MappingRS;

@Table("ROLE")
public class Role implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private String id;
	@Column("NAME")
	@MappingRS("name")
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
