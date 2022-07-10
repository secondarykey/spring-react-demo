package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.springframework.data.relational.core.mapping.Column;

import com.example.demo.model.annotation.MappingRS;

@Embeddable
public class PlanDetailPK implements Serializable {
	public PlanDetailPK() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column("PLANS_ID")
	@MappingRS("plansId")
	private Integer plansId;
	@Column("DETAIL_ID")
	@MappingRS("detailId")
	private Integer detailId;
	public Integer getPlansId() {
		return plansId;
	}
	public void setPlansId(Integer plansId) {
		this.plansId = plansId;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
}
