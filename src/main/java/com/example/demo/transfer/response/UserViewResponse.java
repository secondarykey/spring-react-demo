package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.User;
import com.example.demo.transfer.Paging;

public class UserViewResponse {

	private List<User> users;
	private Paging paging;
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
