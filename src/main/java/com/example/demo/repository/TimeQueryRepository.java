package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Times;
import com.example.demo.repository.core.QueryRepository;
import com.example.demo.transfer.Paging;


@Repository
public class TimeQueryRepository extends QueryRepository {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TimeQueryRepository.class);

	@Autowired(required=true)
	public TimeQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Times> findPage(Paging paging) {
		String sql = """
			SELECT
			  %s 
			FROM TIMES 
		""";
		QuerySet qs = QuerySet.create(Times.class, "", "");
		SQLBuilder builder = SQLBuilder.create(qs);

		builder.setSQL(sql);
		builder.setOrder("""
				ORDER BY "VALUE" DESC,ID ASC 
				""");
		builder.setPaging(paging);
		
		List<Row> rows = this.query(builder);
		List<Times> list = new ArrayList<>();
		for ( Row row : rows ) {
			list.add(row.get(qs));
		}
		return list;
	}
}
