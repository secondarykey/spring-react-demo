package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
	@Id
	@Column("id")
	private String id;
	@Column("name")
	private String name;
	@Column("password")
	private String password;
	@Column("role")
	private String role;
	@Column("expiry")
	private Date expiry;

	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public Date getExpiry() {
		return expiry;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
}
