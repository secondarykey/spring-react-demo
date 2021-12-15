package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class AccessRepository {

	//NamedParameterJdbcTemplate;
	protected final JdbcTemplate template;
	public AccessRepository(JdbcTemplate template) {
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

	/**
	 * カラム名と違う場合、エラーになる為、廃止
	 * @param <T>
	 * @param zz
	 * @param sql
	 * @param objects
	 * @return
	 */
	@Deprecated
	protected <T> List<T> select(Class<T> zz,String sql,Object ...objects) {
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(zz);
		List<T> rtns = template.query(sql, rowMapper,objects);
		return rtns;
	}

	/**
	 * 非推奨 CrudRepositoryを利用すること
	 * @param sql
	 * @param objects
	 * @return
	 */
	@Deprecated
	protected Number insert(String sql,Object ...objects) {
		
		final PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int idx = 1;
				//TODO なんかいい方法ないか調べる
				for ( Object obj : objects ) {
					if ( obj instanceof String ) {
						ps.setString(idx, (String)obj);
					} else if ( obj instanceof Date ) {
						ps.setTimestamp(idx, new Timestamp(((Date)obj).getTime()));
					} else if ( obj instanceof Integer ) {
						ps.setInt(idx, (Integer)obj);
					} else if ( obj instanceof Long ) {
						ps.setLong(idx, (Long)obj);
					} else if ( obj instanceof Float ) {
						ps.setFloat(idx, (Float)obj);
					} else {
						ps.setObject(idx, obj);
					}

					idx++;
				}
				return ps;
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(psc, keyHolder);
		
		//TODO 自動でできないか確認
		if ( keyHolder.getKeys().size() > 1 ) {
			Map<String, Object> keys = keyHolder.getKeys();
			return (Number)keys.get("id");
		}
	
		return keyHolder.getKey();
	}
	/**
	 * 非推奨 CrudRepositoryを利用すること
	 * @param sql
	 * @param objects
	 * @return
	 */
	@Deprecated
	protected int update(String sql,Object ...objects) {
		return template.update(sql, objects);
	}
}
