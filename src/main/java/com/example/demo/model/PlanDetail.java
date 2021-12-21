package com.example.demo.model;

import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("PLAN_DETAILS")
public class PlanDetail {
	@Id
	@Column("ID")
	private Integer id;
	@Column("PLANS_ID")
	private Integer plansId;
	@Column("START")
	private Date start;
	@Column("END")
	private Date end;
	@Column("NAME")
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
