package com.example.demo.transfer.request;

public class PasswordRequest extends Arguments {
	private String userId;
	private String oldPassword;
	private String newPassword;
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
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
