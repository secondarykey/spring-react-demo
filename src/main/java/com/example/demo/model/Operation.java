package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.anotation.model.MappingRS;

@Table("OPERATION")
public class Operation implements Model {
	@Id
	@Column("ID")
	@MappingRS("id")
	private int id;
	
	@Column("OPERATION_ID")
	@MappingRS("opeID")
	private int opeID;

	@Column("ORGANIZATION_ID")
	@MappingRS("orgID")
	private int orgID;

	@Column("SEQ")
	@MappingRS("seq")
	private int seq;

	@Column("START")
	@MappingRS(value="start",method="getDate")
	private Date start;
	
	@Column("END")
	@MappingRS(value="end",method="getDate")
	private Date end;

	@Transient
	private String name;	

	public int getOpeID() {
		return opeID;
	}

	public void setOpeID(int opeID) {
		this.opeID = opeID;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


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
}
