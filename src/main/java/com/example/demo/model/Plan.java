package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("PLANS")
public class Plan {
	@Id
	@Column("ID")
	private Integer id;
	@Column("DATE")
	private Date date;

	@Column("PLACES_ID")
	private Integer placesId;

	@Transient
	private Place place;
	@Transient
	private List<PlanDetail> details;

	public List<PlanDetail> getDetails() {
		return details;
	}

	public void setDetails(List<PlanDetail> details) {
		this.details = details;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getPlacesId() {
		return placesId;
	}

	public void setPlacesId(Integer placesId) {
		this.placesId = placesId;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
