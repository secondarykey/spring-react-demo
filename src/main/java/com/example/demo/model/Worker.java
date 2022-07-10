package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("WORKER")
public class Worker implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("OPERATION_ID")
	@MappingRS("opeID")
	private int opeID;

	@Column("USER_ID")
	@MappingRS("userID")
	private String userID;

	@Column("DATE")
	@MappingRS(value="date",method="getDate")
	private Date date;
	
	@Transient
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOpeID() {
		return opeID;
	}

	public void setOpeID(int opeID) {
		this.opeID = opeID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}	
}
