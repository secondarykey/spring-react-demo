package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Organization;

@Repository
public class OrganizationQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OrganizationQueryRepository.class);

	@Autowired(required=true)
	public OrganizationQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Organization> find(Date day) {
		String sql = """
		SELECT %s FROM ORGANIZATION
		  WHERE 
		    "START" <= ? AND "END" >= ?
        """;

		QuerySet orgQs = QuerySet.create(Organization.class,"", "");
		SQLBuilder builder = SQLBuilder.create(orgQs);
	
		builder.setSQL(sql, day,day);

		List<Row> rows = this.query(builder);
		List<Organization> list = new ArrayList<>();
		for ( Row row : rows ) {
			list.add(row.get(orgQs));
		}
		return list;
	}

}
