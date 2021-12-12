package com.example.demo.model;

import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("plan_details")
public class PlanDetail {
	@Id
	private Integer id;
	@Column("plans_id")
	private Integer plansId;
	private Date start;
	private Date end;
	private String name;
	
	@Transient
	private Plan plan;
	
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlansId() {
		return plansId;
	}
	public void setPlansId(Integer plansId) {
		this.plansId = plansId;
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

}
