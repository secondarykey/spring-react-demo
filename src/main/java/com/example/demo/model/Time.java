package com.example.demo.model;


import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("times")
public class Time {
	@Id
	private int id;
	
	private String value;

	@Column("date")
	private Date date;
	@Column("time")
	private Date time;

	@Column("date_without")
	private Date dateToWithout;
	@Column("date_with")
	private Date dateToWith;
	@Column("offset_without")
	private OffsetDateTime offsetToWithout;
	@Column("offset_with")
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
	public Date getDateToWith() {
		return dateToWith;
	}
	public void setDateToWith(Date dateToWith) {
		this.dateToWith = dateToWith;
	}
	public OffsetDateTime getOffsetToWithout() {
		return offsetToWithout;
	}
	public void setOffsetToWithout(OffsetDateTime offsetToWithout) {
		this.offsetToWithout = offsetToWithout;
	}
	public OffsetDateTime getOffsetToWith() {
		return offsetToWith;
	}
	public void setOffsetToWith(OffsetDateTime offsetToWith) {
		this.offsetToWith = offsetToWith;
	}
}
