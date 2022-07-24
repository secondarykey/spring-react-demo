package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.PlanSet;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Places;
import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;
import com.example.demo.repository.core.QueryRepository;
import com.example.demo.util.DateUtil;

@Repository
public class PlanQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PlanQueryRepository.class);

	@Autowired(required=true)
	public PlanQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public Collection<PlanSet> joinDetail() {
		String sql = """
		SELECT
		  %s
		FROM "PLANS"
		    INNER JOIN (SELECT * FROM PLACES) "PLACE" ON "PLANS"."PLACES_ID" = "PLACE"."ID"
		    LEFT OUTER JOIN (SELECT * FROM PLAN_DETAILS) "PLAN_DETAILS" ON "PLANS"."ID" = "PLAN_DETAILS"."PLANS_ID"
		""";

		QuerySet planQs = QuerySet.create(Plans.class, "PLANS", "plans");
		QuerySet detailQs = QuerySet.create(PlanDetails.class, "PLAN_DETAILS", "plan_details");
		QuerySet placeQs = QuerySet.create(Places.class, "PLACE", "place");
		
		SQLBuilder builder = SQLBuilder.create(planQs,detailQs,placeQs);
		builder.setSQL(sql);
		
	
		List<Row> rows = this.query(builder);
		List<PlanSet> list = new ArrayList<>();
		Map<Integer,PlanSet> exists = new HashMap<>();
		for ( Row row : rows ) {
			
			Plans plan = row.get(planQs);
			PlanDetails detail = row.get(detailQs);
	
			PlanSet set = exists.get(plan.getId());
			if ( set == null ) {
				set = new PlanSet(plan);
				list.add(set);
				exists.put(plan.getId(),set);
			}
			set.addDetail(detail);
		}
		return list;
	}

	public List<Plans> findByPlace(Integer placeId) {
		String sql = """
		SELECT
		  %s
		FROM "PLANS"
		    INNER JOIN (SELECT * FROM PLACES) "PLACE"
		    ON "PLANS"."PLACES_ID" = "PLACE"."ID" AND "PLANS"."PLACES_ID" = ?
		""";
		QuerySet planQs = QuerySet.create(Plans.class, "PLANS", "plans");
		QuerySet placeQs = QuerySet.create(Places.class, "PLACE", "place");
		SQLBuilder builder = SQLBuilder.create(planQs,placeQs);
		builder.setSQL(sql, placeId);
		
		List<Plans> list = new ArrayList<>();
	
		List<Row> rows = query(builder);
		for ( Row row : rows ) {
			list.add(row.get(planQs));
		}
		return list;
	}

	public Plans findByPlaceDate(Integer placeId, Date date) {
		String sql = """
		SELECT %s FROM PLANS WHERE PLACES_ID = ? AND DATE = ?
		""";

		QuerySet planQs = QuerySet.create(Plans.class, "PLANS", "plans");
		SQLBuilder builder = SQLBuilder.create(planQs);
		
		builder.setSQL(sql, placeId,DateUtil.formatClient(date));

		Row row = this.singleQuery(builder);
		return row.get(planQs);
	}
}
