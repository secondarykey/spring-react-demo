package com.example.demo.mapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

public class CountMapper implements RowCallbackHandler {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CountMapper.class);
	private int count;
	
	public int result() {
		return count;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		
		count = rs.getInt("COUNT(*)");
		
		ResultSetMetaData meta = rs.getMetaData();
		int cnt = meta.getColumnCount();
		if ( cnt > 1 ) {
			logger.warn("Count {}",cnt);
		}
	}

}
