package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;

@Repository
public class PlanDetailQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public PlanDetailQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<PlanDetails> findByPlaceDate(Integer placeId, Date date) {
		String sql = """
		SELECT
		  %s
		FROM PLAN_DETAILS
		  INNER JOIN (
		    SELECT * FROM PLANS WHERE PLANS.PLACES_ID = ? AND PLANS.DATE = ?
		  ) PLANS ON PLANS.ID = PLAN_DETAILS.PLANS_ID
		""";

		QuerySet planQs = QuerySet.create(Plans.class, "PLANS", "plans");
		QuerySet detailQs = QuerySet.create(PlanDetails.class, "PLAN_DETAILS", "plan_details");
		SQLBuilder builder = SQLBuilder.create(planQs,detailQs);
		builder.setSQL(sql,placeId,date);

		List<Row> rows = this.query(builder);
		List<PlanDetails> list = new ArrayList<>();
		for ( Row row : rows ) {
			list.add(row.get(detailQs));
		}
		return list;
	}
}
