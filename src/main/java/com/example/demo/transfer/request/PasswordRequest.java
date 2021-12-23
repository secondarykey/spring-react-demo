package com.example.demo.transfer.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.Util;

public class PasswordRequest extends Arguments {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PasswordRequest.class);

	@NotEmpty(message="PRFN00L101")
	private String userId;
	@NotEmpty(message="PRFN00L102")
	private String oldPassword;
	@NotEmpty(message="PRFN00L202")
	private String newPassword1;
	@NotEmpty(message="PRFN00L203")
	private String newPassword2;

	public String getUserId() {
		return userId;
	}
	public void setId(String id) {
		this.userId = id;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword1() {
		return newPassword1;
	}
	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}
	public String getNewPassword2() {
		return newPassword2;
	}
	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

	@AssertTrue(message="PRFN00M201")
	public boolean isPasswordCheck() {
		if ( !Util.equals(this.getNewPassword1() ,this.getNewPassword2()) ) {
			return false;
		}
		return true;
	}
}
