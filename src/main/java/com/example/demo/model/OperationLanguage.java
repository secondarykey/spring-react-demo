package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("OPERATION_LANGUAGE")
public class OperationLanguage implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("NAME")
	@MappingRS("name")
	private String name;	

	@Column("LANGUAGE")
	@MappingRS("lang")
	private String lang;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}	
}
