package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.model.query.PlanDetailRowMapper;
import com.example.demo.model.query.QuerySet;
import com.example.demo.model.query.SQLBuilder;

@Repository
public class PlanDetailQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public PlanDetailQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<PlanDetail> findByPlaceDate(Integer placeId, Date date) {
		String sql = """
		SELECT
		  %s
		FROM PLAN_DETAILS
		  INNER JOIN (
		    SELECT * FROM PLANS WHERE PLANS.PLACES_ID = ? AND PLANS.DATE = ?
		  ) PLANS ON PLANS.ID = PLAN_DETAILS.PLANS_ID
		""";
		SQLBuilder builder = SQLBuilder.create(
				QuerySet.create(Plan.class, "PLANS", "plans"),
				QuerySet.create(Plan.class, "PLAN_DETAILS", "plan_details")
			);
		builder.setSQL(sql,placeId,date);

		PlanDetailRowMapper mapper = new PlanDetailRowMapper(builder);
		this.query(mapper);

		return mapper.get();
	}
}
