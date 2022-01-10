package com.example.demo.transfer.request;

import com.example.demo.transfer.Paging;

public class UserSearchRequest {

	private String id;
	private String name;
	private Paging paging;
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
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
