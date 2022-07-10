package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.model.core.Model;

@Table("PLANS")
public class Plan implements Model {

	@Id
	@Column("ID")
	@MappingRS("id")
	private Integer id;

	@Column("DATE")
	@MappingRS("date")
	private Date date;

	@Column("PLACES_ID")
	@MappingRS("placesId")
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
