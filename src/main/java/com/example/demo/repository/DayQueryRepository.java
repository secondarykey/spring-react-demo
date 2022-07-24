package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.Exp;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Day;
import com.example.demo.repository.core.QueryRepository;
import com.example.demo.util.DateUtil;

@Repository
public class DayQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DayQueryRepository.class);

	@Autowired(required=true)
	public DayQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Day> find(int orgId,Date day) {

		QuerySet dayQs = QuerySet.create(Day.class,"", "");
		SQLBuilder builder = SQLBuilder.create(dayQs);
	
		//TODO 2日前
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.MONTH, -2);
	
		dayQs.setWhere(Exp.ge(Day.DAY,DateUtil.toSQLDate(cal.getTime())).and(Exp.eq(Day.ORGANIZATION_ID,orgId)));

		List<Row> rows = builder.getRows(this);

		List<Day> days = new ArrayList<>();
		for ( Row row : rows ) {
			days.add(row.get(dayQs));
		}
		return days;
	}

}
