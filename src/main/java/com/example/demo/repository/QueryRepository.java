package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.model.query.ModelMapper;

public class QueryRepository {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(QueryRepository.class);

	//NamedParameterJdbcTemplate;
	protected final JdbcTemplate template;
	public QueryRepository(JdbcTemplate template) {
		this.template = template;
	}
	
	protected <T> T query(ModelMapper<T> mapper) {
		template.query(mapper.getSQL(),mapper,mapper.getArguments());
		return mapper.get();
	}

}
