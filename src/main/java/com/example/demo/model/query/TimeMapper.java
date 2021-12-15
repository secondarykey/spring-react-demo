package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Time;
import com.example.demo.util.DateUtil;

public class TimeMapper implements RowCallbackHandler {
	
	private List<Time> list = new ArrayList<>();

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Time time = new Time();
		time.setId(rs.getInt("id"));
		time.setValue(rs.getString("value"));
		time.setDate(rs.getDate("date"));
		time.setTime(rs.getTime("time"));

		time.setDateToWithout(rs.getTimestamp("date_without"));
		time.setDateToWith(rs.getTimestamp("date_with"));

		time.setOffsetToWithout(DateUtil.zone(rs.getTimestamp("offset_without"),"UTC"));
		time.setOffsetToWith(DateUtil.zone(rs.getTimestamp("offset_with"),"UTC"));

		list.add(time);
	}
	

	public List<Time> getResult() {
		return list;
	}
}
