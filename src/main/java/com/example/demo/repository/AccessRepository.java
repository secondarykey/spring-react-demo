package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.demo.util.DateUtil;

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
				//TODO なんかいい方法ないか調べる
				for ( Object obj : objects ) {
					if ( obj instanceof String ) {
						ps.setString(idx, (String)obj);
					} else if ( obj instanceof Date ) {
						ps.setTimestamp(idx, new Timestamp(((Date)obj).getTime()));
					} else if ( obj instanceof LocalDateTime ) {
						LocalDateTime time = (LocalDateTime)obj;
						DateUtil.debug("setObject()",time);
					    ps.setObject(idx, time.atOffset(ZoneOffset.ofHours(-5)));
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

	protected int update(String sql,Object ...objects) {
		return template.update(sql, objects);
	}
}
