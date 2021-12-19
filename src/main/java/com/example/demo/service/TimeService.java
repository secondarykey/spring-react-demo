package com.example.demo.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Time;
import com.example.demo.repository.TimeQueryRepository;
import com.example.demo.repository.TimeRepository;
import com.example.demo.transfer.Paging;
import com.example.demo.transfer.request.TimeInsertRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.TimeViewResponse;
import com.example.demo.util.DateUtil;

@Service
public class TimeService extends BusinessService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TimeService.class);
	
	@Autowired
	private TimeRepository crud;
	@Autowired
	private TimeQueryRepository query;

	public Result<TimeViewResponse> find(Paging paging) {

		long cnt = crud.count();
		if ( cnt == 0 )  {
			//TODO エラー
		}
		paging.setDbCount((int) cnt);
		List<Time> times = query.findPage(paging);
		if ( times.size() <= 0 ) {
			//TODO エラー
		}

		TimeViewResponse res = new TimeViewResponse();
		res.setTimes(times);
		res.setPaging(paging);

		Result<TimeViewResponse> result = new Result<TimeViewResponse>();
		result.setResult(res);
		return result;
	}

	@Transactional
	public Result<TimeViewResponse> insert(TimeInsertRequest json) {
		
		String val = json.getValue();
		String zone = json.getZone();
	
		Date date = DateUtil.parseClient(val);
		OffsetDateTime offset = DateUtil.zone(date, zone);

		Time time = new Time();
		time.setValue(val + " " + zone);
		time.setDate(date);
		time.setTime(date);
		time.setDateToWithout(date);
		time.setOffsetToWith(offset);
		
		crud.save(time);

		Paging paging = json.getPaging();
		return find(paging);
	}
}
