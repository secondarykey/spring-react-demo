package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Session;
import com.example.demo.model.Day;
import com.example.demo.service.DayService;
import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.request.DayRequest;
import com.example.demo.transfer.response.DayResponse;
import com.example.demo.transfer.response.OrganizationTree;
import com.example.demo.transfer.response.Result;
import com.example.demo.util.Util;

/**
 * その日の休日と組織を取得
 * 
 * 
 * 
 */
@RestController
@RequestMapping("/api/demo/day")
@ConditionalOnWebApplication
public class DayController extends JSONController {

	private final static Logger logger = LoggerFactory.getLogger(DayController.class);

    @Autowired
    private DayService dayService;
    @Autowired
    private OrganizationService orgService;
    @Autowired
    private Session session;

	@RequestMapping(value = "/holiday",method = RequestMethod.POST)
	public Result<List<Day>> holiday(@RequestBody DayRequest json) {
		int belong = session.getBelong();
		List<Day> days = dayService.find(belong,json.getDay());
		if ( Util.isEmpty(days) ) {
			logger.warn("休日が０件です。{}:{}",belong,json.getDay());
		}
		Result<List<Day>> result = new Result<>();
		result.setResult(days);
		return result;
	}

	@RequestMapping(value = "/organization",method = RequestMethod.POST)
	public Result<OrganizationTree> organization(@Validated @RequestBody DayRequest json) {
		int belong = session.getBelong();
		OrganizationTree tree = orgService.createTree(belong, json.getDay());
		if ( tree == null ) {
			logger.warn("組織({})のツリーが作成できていません。{}",belong,json.getDay());
		}

		Result<OrganizationTree> result = new Result<>();
		result.setResult(tree);
		return result;
	}

	@RequestMapping(value = "/all",method = RequestMethod.POST)
	public Result<DayResponse> all(@Validated @RequestBody DayRequest json) {
		
		DayResponse res = new DayResponse();

		int belong = session.getBelong();
		List<Day> days = dayService.find(belong,json.getDay());
		if ( Util.isEmpty(days) ) {
			logger.warn("休日が０件です。{}:{}",belong,json.getDay());
		}
		res.setDays(days);

		OrganizationTree tree = orgService.createTree(belong, json.getDay());
		if ( tree == null ) {
			logger.warn("組織({})のツリーが作成できていません。{}",belong,json.getDay());
		}
		res.setOrg(tree);
		Result<DayResponse> result = new Result<>();
		result.setResult(res);
		return result;
	}
	
}
