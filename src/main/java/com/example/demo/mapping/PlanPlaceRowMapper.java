package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;

public class PlanPlaceRowMapper extends ModelMapper<List<Plan>> {
	
	public PlanPlaceRowMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Plan> planList = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Plan plan = map.get(Plan.class);
		Place place = map.get(Place.class);
		plan.setPlace(place);
		
		planList.add(plan);
	}

	@Override
	public List<Plan> get() {
		return planList;
	}
}
