package com.example.demo.transfer;

import java.io.Serializable;

import com.example.demo.model.User;

public class LoginUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String role;
	private int belong;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpirs(String expiry) {
		this.expiry = expiry;
	}
	private String expiry;

	public static LoginUser convert(User user) {
		LoginUser lu = new LoginUser();
		
		lu.id = user.getId();
		lu.name = user.getName();
		lu.role = user.getRole();
		lu.expiry = user.getExpiry().toString();
		lu.belong = user.getBelong();
		
		return lu;
	}
	public int getBelong() {
		return belong;
	}
	public void setBelong(int belong) {
		this.belong = belong;
	}
}
