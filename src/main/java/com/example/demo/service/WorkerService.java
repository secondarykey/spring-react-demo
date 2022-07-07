package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Operation;
import com.example.demo.model.Worker;
import com.example.demo.repository.OperationQueryRepository;
import com.example.demo.repository.WorkerQueryRepository;
import com.example.demo.transfer.response.OperationResponse;
import com.example.demo.transfer.response.WorkerResponse;
import com.example.demo.transfer.response.Workers;
import com.example.demo.util.DateUtil;
import com.example.demo.util.Util;

@Service
public class WorkerService {
	
	private final static Logger logger = LoggerFactory.getLogger(WorkerService.class);

	@Autowired
	OperationQueryRepository ope;
	@Autowired
	WorkerQueryRepository worker;
	
	public WorkerResponse find(int orgID,String dateBuf,String lang) {
		
		WorkerResponse res = new WorkerResponse();
		
		Date date = DateUtil.parseDate(dateBuf);
		List<Operation> list = ope.find(orgID, date, lang);
		if ( Util.isEmpty(list) ) {
			return null;
		}
		
		List<Worker> workers = worker.find(orgID, date);
		if ( Util.isEmpty(workers) ) {
			logger.info("{} の作業者がいません",date);
		}
		
		Map<Integer,List<Worker>> workerMap = new HashMap<>();
		for ( Worker worker : workers ) {
			int id = worker.getOperationId();
			List<Worker> work = workerMap.get(id);
			if ( Util.isEmpty(work) ) {
				work = new ArrayList<>();
				workerMap.put(id, work);
			}
			work.add(worker);
		}

		Workers tempWorker = new Workers();
		//TODO 仮
		Workers second = new Workers();
		//作業数回設定する
		for ( Operation ope : list ) {
			OperationResponse opeRes = new OperationResponse();
			opeRes.setOperation(ope);
			res.addOperation(opeRes);
			tempWorker.addWorkers(workerMap.get(ope.getId()));
			second.addWorkers(null);
		}

		res.addWorkers(tempWorker);
		res.addWorkers(second);

		return res;
	}
}
