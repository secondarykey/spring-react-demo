package com.example.demo.model;


import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TIMES")
public class Time {
	@Id
	@Column("ID")
	private int id;
	
	@Column("VALUE")
	private String value;

	@Column("DATE")
	private Date date;
	@Column("TIME")
	private Date time;

	@Column("DATE_WITHOUT")
	private Date dateToWithout;
	@Column("OFFSET_WITH")
	private OffsetDateTime offsetToWith;

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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getDateToWithout() {
		return dateToWithout;
	}
	public void setDateToWithout(Date dateToWithout) {
		this.dateToWithout = dateToWithout;
	}

	public OffsetDateTime getOffsetToWith() {
		return offsetToWith;
	}
	public void setOffsetToWith(OffsetDateTime offsetToWith) {
		this.offsetToWith = offsetToWith;
	}
}
