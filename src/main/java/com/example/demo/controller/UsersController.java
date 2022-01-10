package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.transfer.request.UserSearchRequest;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.UserViewResponse;

@RestController
@RequestMapping("/api/demo/users")
@ConditionalOnWebApplication
public class UsersController {
	@Autowired
	UserService service;

	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public Result<UserViewResponse> search(@RequestBody UserSearchRequest json) {
		return service.search(json.getId(),json.getName(),json.getPaging());
	}
}
