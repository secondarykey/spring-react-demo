package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.OrganizationMapper;
import com.example.demo.mapping.QuerySet;
import com.example.demo.mapping.SQLBuilder;
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

		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Organization.class,"", "")
		);
	
		builder.setSQL(sql, day,day);

		OrganizationMapper mapper = new OrganizationMapper(builder);
		this.query(mapper);
		return mapper.get();
	}

}
