package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Plan;
import com.example.demo.util.MappingUtil;

public class PlanMapper implements RowCallbackHandler {
	
	private Plan plan = null;

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		plan = MappingUtil.createPlan("",rs);
	}

	public Plan getResult() {
		return plan;
	}
}
