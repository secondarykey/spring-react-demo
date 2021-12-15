package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.util.MappingUtil;

public class PlanDetailRowMapper implements RowCallbackHandler {
	
	private List<PlanDetail> details = new ArrayList<>();

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		PlanDetail detail = MappingUtil.createPlanDetail("plan_details.",rs);
		Plan plan = MappingUtil.createPlan("plans.",rs);
		detail.setPlan(plan);
		details.add(detail);
	}

	public List<PlanDetail> getResult() {
		return details;
	}
}
