package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Day;
import com.example.demo.repository.DayQueryRepository;
import com.example.demo.util.DateUtil;

@Service
public class DayService  extends BusinessService {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(DayService.class);

	@Autowired(required=true)
	DayQueryRepository query;

	public List<Day> find(int belong,String startBuf) {
		Date start = DateUtil.parseDate(startBuf);
		return query.find(belong, start);
	}
}
