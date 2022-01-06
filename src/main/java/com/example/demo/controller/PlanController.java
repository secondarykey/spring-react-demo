package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PlanService;
import com.example.demo.transfer.request.PlanDetailViewRequest;
import com.example.demo.transfer.request.PlanEditRequest;
import com.example.demo.transfer.request.PlanViewRequest;
import com.example.demo.transfer.response.PlanDetailViewResponse;
import com.example.demo.transfer.response.PlanViewResponse;
import com.example.demo.transfer.response.Result;

@RestController
@RequestMapping("/api/demo/plan")
@ConditionalOnWebApplication
public class PlanController extends JSONController {

    @Autowired
    private PlanService planService;

	@RequestMapping("/view")
	public Result<PlanViewResponse> view(@RequestBody PlanViewRequest json) {
		Result<PlanViewResponse> result = planService.view(json);
		if ( result.existMessage() ) {
			FatalError(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}

	@RequestMapping("/detail/view")
	public Result<PlanDetailViewResponse> view(@RequestBody PlanDetailViewRequest json) {
		Result<PlanDetailViewResponse> result = planService.viewDetail(json);
		if ( result.existMessage() ) {
			FatalError(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}
	@RequestMapping("/edit")
	public Result<String> edit(@RequestBody PlanEditRequest json) {
		Result<String> result = planService.insertDetail(json);
		if ( result.existMessage() ) {
			FatalError(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}
}
