package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;
import com.example.demo.util.Util;

@RestController
@RequestMapping("/api/v1")
public class LoginController extends JSONController {

    @Autowired
    private UserService userService;

	@RequestMapping("/login")
	public Result<LoginResponse> certification(@RequestBody LoginRequest json) {
		Result<LoginResponse> result = userService.findByPassword(json);
		if ( !Util.isEmpty(result.getMessageID()) ) {
			Unauthorized(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}
	
	@RequestMapping("/password")
	public Result<LoginResponse> changePassword(@RequestBody PasswordRequest json) {
		Result<LoginResponse> result = userService.changePassword(json);
		if ( !Util.isEmpty(result.getMessageID()) ) {
			Unauthorized(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}
}
