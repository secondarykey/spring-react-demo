package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("plans")
public class Plan {
	@Id
	private Integer id;
	private Date date;

	@Column("places_id")
	private Integer placesId;

	@MappedCollection(idColumn="id",keyColumn="plans_id")
    private List<PlanDetail> detailList;

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
	public void setPlacesId(Integer places_id) {
		this.placesId = places_id;
	}

	public List<PlanDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<PlanDetail> detailList) {
		this.detailList = detailList;
	}

}
