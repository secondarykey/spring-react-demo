package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.anotation.model.MappingRS;

@Table("DAY")
public class Organization implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("ORGANIZATION_ID")
	@MappingRS("orgID")
	private int orgID;

	@Column("PARENT")
	@MappingRS("parent")
	private int parent;

	@Column("NAME")
	@MappingRS("name")
	private String name;
	
	@Column("START")
	@MappingRS(value="start",method="getDate")
	private Date start;
	@Column("END")
	@MappingRS(value="end",method="getDate")
	private Date end;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrgID() {
		return orgID;
	}

	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
	
}
