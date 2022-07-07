package com.example.demo.service;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserQueryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.LoginUser;
import com.example.demo.transfer.Paging;
import com.example.demo.transfer.request.LoginRequest;
import com.example.demo.transfer.request.PasswordRequest;
import com.example.demo.transfer.response.LoginResponse;
import com.example.demo.transfer.response.Result;
import com.example.demo.transfer.response.UserViewResponse;
import com.example.demo.util.DateUtil;
import com.example.demo.util.EncryptUtil;
import com.example.demo.util.Util;

@Service
public class UserService  extends BusinessService {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired(required=true)
	UserRepository crud;
	@Autowired(required=true)
	UserQueryRepository query;

	public Result<LoginResponse> findByPassword(LoginRequest json) {

		Result<LoginResponse> result = new Result<>();
		Users user = query.findByPassword(json.getId(), json.getPassword());
		if ( user == null ) {
			result.setMessageId("PRFN00M101", "パスワードが一致するユーザが存在しない|" + json.getId());
			return result;
		}
		
		// 選択している言語がdefaultでない、言語が違う場合、
		// defaultの場合でも、ユーザのデフォルト言語をresultにはセットする
		String selectLanguage = json.getLanguage();
		String lang = user.getLanguage();
		if ( !Util.isDefault(selectLanguage) && !Util.equals(selectLanguage, lang)) {
			user.setLanguage(selectLanguage);
			crud.save(user);
		}
		selectLanguage = lang;

		LoginResponse res = new LoginResponse();
		res.setLanguage(selectLanguage);

		//TODO しっかりチェック
		OffsetDateTime exp = user.getExpiry();
		OffsetDateTime now = DateUtil.zone(new Date(), "UTC");
		if ( now.toEpochSecond() > exp.toEpochSecond() ) {
			result.setMessageId("PRFN00M102", "有効期限切れ|" + json.getId() + "|" + exp.toString());
			return result;
		}

		LoginUser loginUser = LoginUser.convert(user);
		res.setUser(loginUser);
		result.setResult(res);
		return result;
	}

	public Result<LoginResponse> changePassword(PasswordRequest json) {

		Result<LoginResponse> result = new Result<>();
		Users user = query.findByPassword(json.getUserId(), json.getOldPassword());
		if ( user == null ) {
			result.setMessageId("PRFN00M203", "パスワードが一致するユーザが存在しない|" + json.getUserId());
			return result;
		}

		//TODO 履歴
		user.setPassword(EncryptUtil.hashPassword(json.getNewPassword1()));
		Calendar cal = Calendar.getInstance();
		//TODO 外だし
		cal.add(Calendar.DATE, 10);

		OffsetDateTime zone = DateUtil.zone(cal.getTime(),"UTC");
		user.setExpiry(zone);
		crud.save(user);

		LoginResponse res = new LoginResponse();
		res.setUser(LoginUser.convert(user));
		result.setResult(res);

		return result;
	}

	public Result<UserViewResponse> search(String id, String name, Paging paging) {
		
		List<Users> users = query.search(id,name,paging);
	
		UserViewResponse res = new UserViewResponse();
		res.setUsers(users);
		res.setPaging(paging);
		
		Result<UserViewResponse> result = new Result<>();
		result.setResult(res);
		return result;
	}
}
