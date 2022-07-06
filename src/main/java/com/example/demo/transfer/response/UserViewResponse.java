package com.example.demo.transfer.response;

import java.util.List;

import com.example.demo.model.Users;
import com.example.demo.transfer.Paging;

public class UserViewResponse {

	private List<Users> users;
	private Paging paging;
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
