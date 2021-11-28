package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.LoginUser;
import com.example.demo.transfer.response.Result;

@RestController
@RequestMapping("/api/v1")
public class LoginController extends JSONController {

    @Autowired
    private UserService userService;

	@RequestMapping("/login")
	public Result<LoginResponse> certification(@RequestBody LoginRequest json) {

		User user = userService.findByPassword(json.getId(), json.getPassword());

		Result<LoginResponse> rtn = new Result<>();
		LoginResponse res = new LoginResponse();
		rtn.setResult(res);

		if ( user == null ) {
			Unauthorized("PRFN00M101","IDとパスワードが一致しない:" + json.getId());
		} else {
			LoginUser u = LoginUser.convert(user);
			res.setUser(u);
			Date now = new Date();
			if ( now.compareTo(user.getExpiry()) >= 0 ) {
				Unauthorized("PRFN00M102","有効期限切れ:" + user.getExpiry().toString());
			}
		}
		
		return rtn;
	}
	
	@RequestMapping("/password")
	public Result<String> changePassword(@RequestBody PasswordRequest json) {
		User user = userService.findByPassword(json.getUserId(), json.getOldPassword());
		Result<String> rtn = new Result<>();
		
		if ( user == null ) {
			Unauthorized("PRFN00M203","IDとパスワードが一致しない:" + json.getUserId());
		} else {
			user.setPassword(json.getNewPassword());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 10);
			user.setExpiry(cal.getTime());
			int count = userService.updatePassword(user);
			if ( count != 1 ) {
				FatalError("PRFN00M000","アップデート時のカウント数に問題あり:" + count);
			}
		}
		
		return rtn;
	}
}
