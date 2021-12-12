package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.util.MappingUtil;

public class PlanRowMapper implements RowCallbackHandler {
	
	private Map<Integer,Plan> planMap = new HashMap<>();

	@Override
	public void processRow(ResultSet rs) throws SQLException {

		int key = rs.getInt("plans.id");
		Plan plan = planMap.get(key);
		if ( plan == null ) {
			plan = MappingUtil.createPlan("plans.",rs);
			Place place = MappingUtil.createPlace("place.",rs);
			plan.setPlace(place);
			plan.setDetails(new ArrayList<PlanDetail>());
			planMap.put(key, plan);
		}

		List<PlanDetail> list = plan.getDetails();
		PlanDetail detail = MappingUtil.createPlanDetail("plan_details.",rs);
		detail.setPlan(plan);
		list.add(detail);
	}


	public List<Plan> getResult() {
		//TODO OrderByを意識
		return new ArrayList<Plan>(planMap.values());
	}
}
