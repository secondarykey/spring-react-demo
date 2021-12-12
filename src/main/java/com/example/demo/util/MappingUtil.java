package com.example.demo.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;

public class MappingUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MappingUtil.class);
	
	public static PlanDetail createPlanDetail(String prefix, ResultSet rs) throws SQLException {
		PlanDetail detail = new PlanDetail();
		detail.setId(rs.getInt(prefix + "id"));
		detail.setName(rs.getString(prefix + "name"));
		detail.setPlansId(rs.getInt(prefix + "plans_id"));
		detail.setStart(rs.getDate(prefix + "start"));
		detail.setEnd(rs.getDate(prefix + "end"));
		return detail;
	}

	public static Place createPlace(String prefix, ResultSet rs) throws SQLException {
		Place place = new Place();
		place.setId(rs.getInt(prefix + "id"));
		place.setName(rs.getString(prefix + "name"));
		place.setTimezone(rs.getString(prefix + "timezone"));
		return place;
	}

	public static Plan createPlan(String prefix, ResultSet rs) throws SQLException {
		Plan plan = new Plan();
		plan.setId(rs.getInt(prefix + "id"));
		plan.setDate(rs.getDate(prefix + "date"));
		plan.setPlacesId(rs.getInt(prefix + "places_id"));
		return plan;
	}

	public static void printColumn(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd= rs.getMetaData();	
		for ( int idx = 1; idx <= rsmd.getColumnCount(); ++idx ) {
			logger.error("{}-{}:[{}]",idx,rsmd.getColumnClassName(idx),rsmd.getColumnClassName(idx));
			//System.out.println(idx + "-" + rsmd.getColumnName(idx) + "[" + rsmd.getColumnClassName(idx) + "]");
		}
	}
	
}
