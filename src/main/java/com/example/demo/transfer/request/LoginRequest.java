package com.example.demo.transfer.request;

import javax.validation.constraints.NotEmpty;

public class LoginRequest extends Arguments {

	@NotEmpty(message="PRFN00L101")
	private String id;

	@NotEmpty(message="PRFN00L102")
	private String password;

	private String language;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
