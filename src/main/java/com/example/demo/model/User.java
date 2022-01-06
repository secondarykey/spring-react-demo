package com.example.demo.model;

import java.time.OffsetDateTime;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.anotation.model.MappingRS;

@Table("USERS")
public class User implements Persistable<String>,Model {

	@Id
	@Column("ID")
	@MappingRS("id")
	private String id;
	@Transient
	private boolean insert = false;

	@Column("NAME")
	@MappingRS("name")
	private String name;

	@Column("PASSWORD")
	@MappingRS("password")
	private String password;

	@Column("LANGUAGE")
	@MappingRS("language")
	private String language;

	@Column("ROLE")
	@MappingRS("role")
	private String role;

	@Column("BELONG")
	@MappingRS("belong")
	private int belong;

	@Column("EXPIRY")
	@MappingRS("expiry")
	private OffsetDateTime expiry;
	
	@Transient
	private Role roleObj;

	public Role getRoleObj() {
		return roleObj;
	}
	public void setRoleObj(Role roleObj) {
		this.roleObj = roleObj;
	}
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
	
	public void setInsert() {
		insert = true;
	}
	public void setUpdate() {
		insert = false;
	}
	@Override
	public boolean isNew() {
		return insert;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public int getBelong() {
		return belong;
	}
	public void setBelong(int belong) {
		this.belong = belong;
	}
}
