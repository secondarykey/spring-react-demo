package com.example.demo.model.query;

import com.example.demo.model.Plan;

public class PlanMapper extends ModelMapper<Plan> {
	
	public PlanMapper(SQLBuilder builder) {
		super(builder);
	}

	private Plan plan = null;

	@Override
	protected void mapping(MappingObject map) {
		plan = map.get(Plan.class);
	}

	@Override
	public Plan get() {
		return plan;
	}
}
