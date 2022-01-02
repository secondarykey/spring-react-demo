package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;

public class PlanDetailRowMapper extends ModelMapper<List<PlanDetail>> {
	
	public PlanDetailRowMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<PlanDetail> details = new ArrayList<>();
	private Map<Integer,Plan> cache = new HashMap<>();

	@Override
	protected void mapping(MappingObject map) {
		Plan plan = map.get(Plan.class);
		if ( !cache.containsKey(plan.getId()) ) {
			cache.put(plan.getId(), plan);
		} else {
			plan = cache.get(plan.getId());
		}
		PlanDetail detail = map.get(PlanDetail.class);
		details.add(detail);
	}

	@Override
	public List<PlanDetail> get() {
		return details;
	}
}
