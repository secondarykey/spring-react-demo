package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Session;
import com.example.demo.controller.JSONController;
import com.example.demo.service.UserService;
import com.example.demo.transfer.LoginUser;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;
import com.example.demo.util.Util;

@RestController
@RequestMapping("/api/v1")
@ConditionalOnWebApplication
public class LoginController extends JSONController {

	@Autowired
	private Session session;
    @Autowired
    private UserService userService;

	@RequestMapping("/login")
	public Result<LoginResponse> certification(@Validated @RequestBody LoginRequest json) {
	
		Result<LoginResponse> result = userService.findByPassword(json);
		if ( result.existMessage() ) {
			Unauthorized(result.getMessageID(),result.getReason());
			return result;
		}

		LoginResponse obj = result.getResult();
		LoginUser user = obj.getUser();
		session.setUser(user);

		String lang = obj.getLanguage();
		if ( !Util.isEmpty(lang) ) {
			session.setLanguage(lang);
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
