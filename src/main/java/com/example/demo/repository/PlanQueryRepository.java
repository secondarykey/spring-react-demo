package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;
import com.example.demo.model.query.PlanMapper;
import com.example.demo.model.query.PlanPlaceRowMapper;
import com.example.demo.model.query.PlanRowMapper;

@Repository
public class PlanQueryRepository extends QueryRepository {


	@Autowired(required=true)
	public PlanQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Plan> joinDetail() {
		String sql = """
				SELECT
				    "PLANS"."ID" AS "plans.id", "PLANS"."date" AS "plans.date","PLANS"."PLACES_ID" AS "plans.places_id",
				    "PLACE"."ID" AS "place.id", "PLACE"."name" AS "place.name","PLACE"."TIMEZONE" AS "place.timezone",
				    "PLAN_DETAILS"."ID" AS "plan_details.id","PLAN_DETAILS"."PLANS_ID" AS "plan_details.plans_id",
				    "PLAN_DETAILS"."START" AS "plan_details.start","PLAN_DETAILS"."END" AS "plan_details.end",
				    "PLAN_DETAILS"."NAME" AS "plan_details.name"
				  FROM "PLANS"
				    INNER JOIN (SELECT * FROM PLACES) "PLACE" ON "PLANS"."PLACES_ID" = "PLACE"."ID"
				    LEFT OUTER JOIN (SELECT * FROM PLAN_DETAILS) "PLAN_DETAILS" ON "PLANS"."ID" = "PLAN_DETAILS"."PLANS_ID"
				""";

		PlanRowMapper mapper = new PlanRowMapper();
		template.query(sql, mapper);
		return mapper.getResult();
	}

	public List<Plan> findByPlace(Integer placeId) {
		String sql = """
				SELECT
				    "PLANS"."ID" AS "plans.id", "PLANS"."DATE" AS "plans.date", "PLANS"."PLACES_ID" AS "plans.places_id",
				    "PLACE"."ID" AS "place.id", "PLACE"."NAME" AS "place.name", "PLACE"."TIMEZONE" AS "place.timezone"
				  FROM "PLANS"
				    INNER JOIN (SELECT * FROM PLACES) "PLACE"
				    ON "PLANS"."PLACES_ID" = "PLACE"."ID" AND "PLANS"."PLACES_ID" = ?
						""";
		PlanPlaceRowMapper mapper = new PlanPlaceRowMapper();
		template.query(sql, mapper, placeId);
		return mapper.getResult();
	}

	public Plan findByPlaceDate(Integer placeId, Date date) {
		String sql = """
					SELECT PLANS.* FROM PLANS WHERE PLACES_ID = ? AND DATE = ?
				""";
		PlanMapper mapper = new PlanMapper();
		template.query(sql, mapper, placeId, date);
		return mapper.getResult();
	}
}
