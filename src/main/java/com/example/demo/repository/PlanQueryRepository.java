package com.example.demo.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.model.query.PlanMapper;
import com.example.demo.model.query.PlanPlaceRowMapper;
import com.example.demo.model.query.PlanRowMapper;
import com.example.demo.model.query.QuerySet;
import com.example.demo.model.query.SQLBuilder;
import com.example.demo.util.DateUtil;

@Repository
public class PlanQueryRepository extends QueryRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(PlanQueryRepository.class);

	@Autowired(required=true)
	public PlanQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public Collection<Plan> joinDetail() {
		String sql = """
		SELECT
		  %s
		FROM "PLANS"
		    INNER JOIN (SELECT * FROM PLACES) "PLACE" ON "PLANS"."PLACES_ID" = "PLACE"."ID"
		    LEFT OUTER JOIN (SELECT * FROM PLAN_DETAILS) "PLAN_DETAILS" ON "PLANS"."ID" = "PLAN_DETAILS"."PLANS_ID"
		""";
		
		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Plan.class, "PLANS", "plans"),
			QuerySet.create(PlanDetail.class, "PLAN_DETAILS", "plan_details"),
			QuerySet.create(Place.class, "PLACE", "place")
		);
		builder.setSQL(sql);

		PlanRowMapper mapper = new PlanRowMapper(builder);
		this.query(mapper);
		return mapper.get();
	}

	public List<Plan> findByPlace(Integer placeId) {
		String sql = """
		SELECT
		  %s
		FROM "PLANS"
		    INNER JOIN (SELECT * FROM PLACES) "PLACE"
		    ON "PLANS"."PLACES_ID" = "PLACE"."ID" AND "PLANS"."PLACES_ID" = ?
		""";
		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Plan.class, "PLANS", "plans"),
			QuerySet.create(Place.class, "PLACE", "place")
		);
		builder.setSQL(sql, placeId);

		PlanPlaceRowMapper mapper = new PlanPlaceRowMapper(builder);
		this.query(mapper);
		return mapper.get();
	}

	public Plan findByPlaceDate(Integer placeId, Date date) {
		String sql = """
		SELECT %s FROM PLANS WHERE PLACES_ID = ? AND DATE = ?
		""";

		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Plan.class, "PLANS", "plans")
		);
		
		builder.setSQL(sql, placeId,DateUtil.formatClient(date));

		PlanMapper mapper = new PlanMapper(builder);
		this.query(mapper);
		return mapper.get();
	}
}
