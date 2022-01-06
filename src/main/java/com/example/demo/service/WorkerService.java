package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Operation;
import com.example.demo.repository.OperationQueryRepository;
import com.example.demo.transfer.response.OperationResponse;
import com.example.demo.transfer.response.WorkerResponse;
import com.example.demo.util.DateUtil;
import com.example.demo.util.Util;

@Service
public class WorkerService {

	@Autowired
	OperationQueryRepository ope;
	
	public WorkerResponse find(int orgID,String dateBuf,String lang) {
		
		WorkerResponse res = new WorkerResponse();
		
		Date date = DateUtil.parseDate(dateBuf);
		List<Operation> list = ope.find(orgID, date, lang);
		if ( Util.isEmpty(list) ) {
			return null;
		}

		for ( Operation ope : list ) {
			OperationResponse opeRes = new OperationResponse();
			opeRes.setOperation(ope);
			res.addOperation(opeRes);
		}

		return res;
	}
}
