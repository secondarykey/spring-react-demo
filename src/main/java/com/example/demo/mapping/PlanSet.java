package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;

public class PlanSet {

	private Plans plan;
	private List<PlanDetails> details;
	public PlanSet(Plans plan) {
		this.plan = plan;
	}
	public Plans getPlan() {
		return plan;
	}
	public List<PlanDetails> getDetails() {
		return details;
	}
	public void addDetail(PlanDetails detail) {
		if ( details == null ) {
			details = new ArrayList<>();
		}
		details.add(detail);
	}
}
