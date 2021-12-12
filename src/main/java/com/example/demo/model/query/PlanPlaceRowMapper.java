package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.util.MappingUtil;

public class PlanPlaceRowMapper implements RowCallbackHandler {
	
	private List<Plan> planList = new ArrayList<>();

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		MappingUtil.printColumn(rs);
		Plan plan = MappingUtil.createPlan("plans.",rs);
		Place place = MappingUtil.createPlace("place.",rs);
		plan.setPlace(place);
		planList.add(plan);
	}


	public List<Plan> getResult() {
		return planList;
	}
}
