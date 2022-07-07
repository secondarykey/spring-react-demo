package com.example.demo.controller.api.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Session;
import com.example.demo.controller.JSONController;
import com.example.demo.service.WorkerService;
import com.example.demo.transfer.request.WorkerRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.WorkerResponse;

/**
 * 作業の取得
 * 
 * 
 * 
 */
@RestController
@RequestMapping("/api/demo/worker")
@ConditionalOnWebApplication
public class WorkerController extends JSONController {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private WorkerService workerService;
    @Autowired
    private Session session;

	@RequestMapping(value = "/day",method = RequestMethod.POST)
	public Result<WorkerResponse> holiday(@RequestBody WorkerRequest json) {
		String lang = session.getLanguage();
		WorkerResponse res = workerService.find(json.getOrganization(),json.getDay(),lang);
		if ( res == null ) {
			logger.warn("{} の作業は存在しません",json.getDay());
		}
		Result<WorkerResponse> result = new Result<>();
		result.setResult(res);
		return result;
	}

}
