package com.example.demo.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.TimeMapper;
import com.example.demo.mapping.core.Exp;
import com.example.demo.mapping.core.Order;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Time;
import com.example.demo.transfer.Paging;


@Repository
public class TimeQueryRepository extends QueryRepository {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TimeQueryRepository.class);

	@Autowired(required=true)
	public TimeQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Time> findPage(Paging paging) {

		QuerySet qs = QuerySet.create(Time.class, "", "");
		QuerySet childQs = QuerySet.create(Time.class, "", "");
		SQLBuilder builder = SQLBuilder.create(qs);

		qs.where(Exp.eq(Time.VALUE,null).and(Exp.eq(Time.ID,10)));

		builder.setPaging(paging);
		builder.setOrder(Order.desc(Time.VALUE),Order.asc(Time.ID));
		
		qs.addInnerJoin(childQs,Exp.eq(Time.VALUE,null).and(Exp.eq(Time.ID,10)));

		TimeMapper mapper = new TimeMapper(builder);
		this.query(mapper);

		return mapper.get();
	}
}
