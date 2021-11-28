package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class Service {
	//NamedParameterJdbcTemplate;
	protected final JdbcTemplate template;
	public Service(JdbcTemplate template) {
		this.template = template;
	}
	
	protected <T> T get(Class<T> zz,String sql,Object ...objects) {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(zz);
		try {
			T rtn = template.queryForObject(sql,rowMapper,objects);
			return rtn;
		} catch ( EmptyResultDataAccessException ex ) {
			return null;
		}
	}

	protected <T> List<T> select(Class<T> zz,String sql,Object ...objects) {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(zz);
		List<T> rtns = template.query(sql, rowMapper,objects);
		return rtns;
	}

	protected Number insert(String sql,Object ...objects) {
		
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int idx = 1;
				for ( Object obj : objects ) {
					ps.setObject(idx, obj);
					idx++;
				}
				return ps;
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(psc, keyHolder);
	
		return keyHolder.getKey();
	}

	protected int update(String sql,Object ...objects) {
		//TODO Model - BeanPropertySqlが動作しない
		//SqlParameterSource param = new BeanPropertySqlParameterSource(model);
		return template.update(sql, objects);
	}
}
