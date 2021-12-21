package com.example.demo.model;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public class User {

	@Id
	@Column("ID")
	private String id;
	@Column("NAME")
	private String name;
	@Column("PASSWORD")
	private String password;
	@Column("ROLE")
	private String role;
	@Column("EXPIRY")
	private OffsetDateTime expiry;

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
	public OffsetDateTime getExpiry() {
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
	public void setExpiry(OffsetDateTime expiry) {
		this.expiry = expiry;
	}
}
