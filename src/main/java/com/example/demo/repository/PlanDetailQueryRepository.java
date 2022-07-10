package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.PlanDetailRowMapper;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;

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
				QuerySet.create(PlanDetail.class, "PLAN_DETAILS", "plan_details")
			);
		builder.setSQL(sql,placeId,date);

		PlanDetailRowMapper mapper = new PlanDetailRowMapper(builder);
		this.query(mapper);

		return mapper.get();
	}
}
