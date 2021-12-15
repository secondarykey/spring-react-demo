package com.example.demo.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.KnownException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.LoginUser;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;

@Service
public class UserService  extends BusinessService {

	@Autowired(required=true)
	UserRepository repo;

	public Result<LoginResponse> findByPassword(LoginRequest json) {

		Result<LoginResponse> result = new Result<>();
		User user = repo.findByPassword(json.getId(), json.getPassword());
		if ( user == null ) {
			result.setMessage("PRFN00M101", "パスワードが一致するユーザが存在しない|" + json.getId());
			return result;
		}
	
		//TODO しっかりチェック
		Date exp = user.getExpiry();
		Date now = new Date();
		if ( now.after(exp) ) {
			result.setMessage("PRFN00M102", "有効期限切れ|" + json.getId() + "|" + exp.toString());
			return result;
		}

		LoginResponse res = new LoginResponse();
		LoginUser loginUser = LoginUser.convert(user);

		res.setUser(loginUser);
		result.setResult(res);
		return result;
	}

	public Result<String> changePassword(PasswordRequest json) {
		Result<String> result = new Result<>();
		User user = repo.findByPassword(json.getUserId(), json.getOldPassword());
		if ( user == null ) {
			result.setMessage("PRFN00M203", "パスワードが一致するユーザが存在しない|" + json.getUserId());
			return result;
		}

		//TODO 履歴

		user.setPassword(json.getNewPassword());

		//TODO 有効期限
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 10);
		user.setExpiry(cal.getTime());
	
		int cnt = repo.updatePassword(user);
		if ( cnt != 1 ) {
			throw new KnownException(HttpStatus.INTERNAL_SERVER_ERROR,"PRFN00M000","アップデート数に問題あり:" + cnt);
		}
		return result;
	}

}
