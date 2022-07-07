package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;

public class PlansSet {

	private Plans plan;
	private List<PlanDetails> details = new ArrayList<>();
	
	public PlansSet(Plans plan) {
		this.plan = plan;
	}
	
	public void addDetail(PlanDetails detail) {
		getDetails().add(detail);
	}

	public Plans getPlan() {
		return plan;
	}

	public List<PlanDetails> getDetails() {
		return details;
	}

}
