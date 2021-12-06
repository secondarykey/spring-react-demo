package com.example.demo.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.util.DateUtil;

@Repository
public class TimeRepository extends AccessRepository {

	@Autowired(required=true)
	public TimeRepository(JdbcTemplate template) {
		super(template);
	}

	public void insertObject() {
		String sql = "INSERT INTO TIMES(NAME,VALUE,VALUE_W_TZ,VALUE_WO_TZ) VALUES (?,?,?,?)";
		Date now = new Date();
		this.insert(sql,"Java new Date()",now,now,now);
	}

	public void insert() {
		String sql = "INSERT INTO TIMES(NAME,VALUE,VALUE_W_TZ,VALUE_WO_TZ) VALUES ('CURRENT_TIME',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
		this.insert(sql);
	}

	public void insertText() {
		String sql = "INSERT INTO TIMES(NAME,VALUE,VALUE_W_TZ,VALUE_WO_TZ) VALUES (?,?,?,?)";
		Date now = new Date();
		var t = DateUtil.zone(now, "PST");
		this.insert(sql,"Send " + t,t,t,t);
	}
}
