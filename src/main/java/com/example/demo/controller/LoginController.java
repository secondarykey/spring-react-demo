package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;

@RestController
@RequestMapping("/api/v1")
public class LoginController extends JSONController {

    @Autowired
    private UserService userService;

	@RequestMapping("/login")
	public Result<LoginResponse> certification(@Validated @RequestBody LoginRequest json) {
		Result<LoginResponse> result = userService.findByPassword(json);
		if ( result.existMessage() ) {
			Unauthorized(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}

	@RequestMapping("/password")
	public Result<LoginResponse> changePassword(@Validated @RequestBody PasswordRequest json) {
		Result<LoginResponse> result = userService.changePassword(json);
		if ( result.existMessage() ) {
			Unauthorized(result.getMessageID(),result.getReason());
			return result;
		}
		return result;
	}
}
