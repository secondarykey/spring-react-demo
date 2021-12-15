package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlanDetail;
import com.example.demo.model.query.PlanDetailRowMapper;

@Repository
public class PlanDetailQueryRepository extends AccessRepository {

	@Autowired
	public PlanDetailQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<PlanDetail> findByPlaceDate(Integer placeId, Date date) {
		String sql = """
			SELECT
			    "plans"."id" AS "plans.id", "plans"."date" AS "plans.date","plans"."places_id" AS "plans.places_id",
				"plan_details"."id" AS "plan_details.id","plan_details"."plans_id" AS "plan_details.plans_id",
				"plan_details"."start" AS "plan_details.start",
				"plan_details"."end" AS "plan_details.end",
				"plan_details"."name" AS "plan_details.name"
			FROM plan_details
			  INNER JOIN (
			    SELECT * FROM PLANS WHERE PLANS.PLACES_ID = ? AND PLANS.DATE = ?
			  ) plans ON plans.id = plan_details.plans_id
		""";
		PlanDetailRowMapper mapper = new PlanDetailRowMapper();
		template.query(sql, mapper, placeId, date);
		return mapper.getResult();
	}
}
