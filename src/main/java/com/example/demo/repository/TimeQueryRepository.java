package com.example.demo.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Time;
import com.example.demo.model.query.QuerySet;
import com.example.demo.model.query.SQLBuilder;
import com.example.demo.model.query.TimeMapper;
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
		String sql = """
			SELECT
			  %s 
			FROM TIMES 
			  ORDER BY "VALUE" DESC,ID ASC 
			  LIMIT ? OFFSET ?
				""";
		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Time.class, "", "")
		);

		builder.setSQL(sql,paging.getNumberOfDisplay(),paging.getOffset());
		TimeMapper mapper = new TimeMapper(builder);
		this.query(mapper);

		return mapper.get();
	}
}
