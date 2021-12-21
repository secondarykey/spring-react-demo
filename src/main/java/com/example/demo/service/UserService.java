package com.example.demo.service;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserQueryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.LoginUser;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;
import com.example.demo.util.DateUtil;

@Service
public class UserService  extends BusinessService {

	@Autowired(required=true)
	UserRepository crud;
	@Autowired(required=true)
	UserQueryRepository query;

	public Result<LoginResponse> findByPassword(LoginRequest json) {

		Result<LoginResponse> result = new Result<>();
		User user = query.findByPassword(json.getId(), json.getPassword());
		if ( user == null ) {
			result.setMessage("PRFN00M101", "パスワードが一致するユーザが存在しない|" + json.getId());
			return result;
		}
	
		//TODO しっかりチェック
		OffsetDateTime exp = user.getExpiry();
		Date now = new Date();
		if ( now.getTime() > exp.toEpochSecond() ) {
			result.setMessage("PRFN00M102", "有効期限切れ|" + json.getId() + "|" + exp.toString());
			return result;
		}

		LoginResponse res = new LoginResponse();
		LoginUser loginUser = LoginUser.convert(user);

		res.setUser(loginUser);
		result.setResult(res);
		return result;
	}

	public Result<LoginResponse> changePassword(PasswordRequest json) {
		Result<LoginResponse> result = new Result<>();
		User user = query.findByPassword(json.getUserId(), json.getOldPassword());
		if ( user == null ) {
			result.setMessage("PRFN00M203", "パスワードが一致するユーザが存在しない|" + json.getUserId());
			return result;
		}

		//TODO 履歴
		user.setPassword(json.getNewPassword());
		//TODO 有効期限
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 10);

		OffsetDateTime zone = DateUtil.zone(cal.getTime(),"UTC");
		user.setExpiry(zone);
		crud.save(user);

		LoginResponse res = new LoginResponse();
		res.setUser(LoginUser.convert(user));
		result.setResult(res);

		return result;
	}

}
