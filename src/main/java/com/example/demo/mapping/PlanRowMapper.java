package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;

public class PlanRowMapper extends ModelMapper<Collection<Plan>> {
	
	public PlanRowMapper(SQLBuilder builder) {
		super(builder);
	}

	private Map<Integer,Plan> planMap = new LinkedHashMap<>();

	@Override
	protected void mapping(MappingObject map) {
		Plan plan = map.get(Plan.class);

		if ( !planMap.containsKey(plan.getId()) ) {

			plan.setDetails(new ArrayList<PlanDetail>());

			Place place = map.get(Place.class);
			plan.setPlace(place);
			planMap.put(plan.getId(), plan);
		} else {
			plan = planMap.get(plan.getId());
		}

		PlanDetail detail = map.get(PlanDetail.class);
		detail.setPlan(plan);

		List<PlanDetail> details = plan.getDetails();
		details.add(detail);
	}

	@Override
	public Collection<Plan> get() {
		return planMap.values();
	}

}
