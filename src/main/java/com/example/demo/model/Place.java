package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("PLACES")
public class Place implements Model {

	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;

	@Column("NAME")
	@MappingRS("name")
	private String name;

	@Column("TIMEZONE")
	@MappingRS("timezone")
	private String timezone;

	@Transient
	private Plan plan;

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
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}


}
