package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlanDetail;
import com.example.demo.model.query.PlanDetailRowMapper;

@Repository
public class PlanDetailQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public PlanDetailQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<PlanDetail> findByPlaceDate(Integer placeId, Date date) {
		String sql = """
			SELECT
			    "PLANS"."ID" AS "plans.id", 
			    "PLANS"."DATE" AS "plans.date",
			    "PLANS"."PLACES_ID" AS "plans.places_id",
				"PLAN_DETAILS"."ID" AS "plan_details.id",
				"PLAN_DETAILS"."PLANS_ID" AS "plan_details.plans_id",
				"PLAN_DETAILS"."START" AS "plan_details.start",
				"PLAN_DETAILS"."END" AS "plan_details.end",
				"PLAN_DETAILS"."NAME" AS "plan_details.name"
			FROM PLAN_DETAILS
			  INNER JOIN (
			    SELECT * FROM PLANS WHERE PLANS.PLACES_ID = ? AND PLANS.DATE = ?
			  ) PLANS ON PLANS.ID = PLAN_DETAILS.PLANS_ID
		""";
		PlanDetailRowMapper mapper = new PlanDetailRowMapper();
		template.query(sql, mapper, placeId, date);
		return mapper.getResult();
	}
}
