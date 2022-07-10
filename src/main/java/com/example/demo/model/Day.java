package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("DAY")
public class Day implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("ORGANIZATION_ID")
	@MappingRS("orgID")
	private int orgID;
	
	@Column("DAY")
	@MappingRS(value="day",method="getDate")
	private Date day;
	
	@Column("VALUE")
	@MappingRS("value")
	private int value;

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

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int val) {
		this.value = val;
	}
	
}
