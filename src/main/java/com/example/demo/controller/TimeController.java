package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TimeService;
import com.example.demo.transfer.request.TimeInsertRequest;
import com.example.demo.transfer.request.TimeViewRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.TimeViewResponse;

@RestController
@RequestMapping("/api/demo/times")
@ConditionalOnWebApplication
public class TimeController {
	@Autowired
	TimeService service;

	@RequestMapping(value = "/view",method = RequestMethod.POST)
	public Result<TimeViewResponse> view(@RequestBody TimeViewRequest json) {
		return service.find(json.getPaging());
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public Result<TimeViewResponse> register(@Validated @RequestBody TimeInsertRequest json) {
		return service.insert(json);
	}
}
