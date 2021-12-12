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
public class PlanQueryRepository extends AccessRepository {

	@Autowired
	public PlanQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Plan> joinDetail() {
		String sql = """
				SELECT
				    "plans"."id" AS "plans.id", "plans"."date" AS "plans.date","plans"."places_id" AS "plans.places_id",
				    "place"."id" AS "place.id", "place"."name" AS "place.name","place"."timezone" AS "place.timezone",
				    "plan_details"."id" AS "plan_details.id","plan_details"."plans_id" AS "plan_details.plans_id",
				    "plan_details"."start" AS "plan_details.start","plan_details"."end" AS "plan_details.end",
				    "plan_details"."name" AS "plan_details.name"
				  FROM "plans"
				    INNER JOIN (SELECT * FROM PLACES) "place" ON "plans"."places_id" = "place"."id"
				    LEFT OUTER JOIN (SELECT * FROM PLAN_DETAILS) "plan_details" ON "plans"."id" = "plan_details"."plans_id"
				""";

		PlanRowMapper mapper = new PlanRowMapper();
		template.query(sql, mapper);
		return mapper.getResult();
	}

	public List<Plan> findByPlace(Integer placeId) {
		String sql = """
				SELECT
				    "plans"."id" AS "plans.id", "plans"."date" AS "plans.date", "plans"."places_id" AS "plans.places_id",
				    "place"."id" AS "place.id", "place"."name" AS "place.name", "place"."timezone" AS "place.timezone"
				  FROM "plans"
				    INNER JOIN ( SELECT * FROM PLACES) "place"
				    ON "plans"."places_id" = "place"."id" AND "plans"."places_id" = ?
						""";
		PlanPlaceRowMapper mapper = new PlanPlaceRowMapper();
		template.query(sql, mapper, placeId);
		return mapper.getResult();
	}

	public Plan findByPlaceDate(Integer placeId, Date date) {
		String sql = """
					SELECT plans.* FROM PLANS WHERE PLACES_ID = ? AND DATE = ?
				""";
		PlanMapper mapper = new PlanMapper();
		template.query(sql, mapper, placeId, date);
		return mapper.getResult();
	}
}
