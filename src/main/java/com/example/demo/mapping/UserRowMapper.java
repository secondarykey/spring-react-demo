package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.User;

public class UserRowMapper extends ModelMapper<List<User>> {
	
	private List<User> users = new ArrayList<>();

	public UserRowMapper(SQLBuilder builder) {
		super(builder);
	}

	@Override
	protected void mapping(MappingObject map) {
		User user = map.get(User.class);
		users.add(user);
	}

	@Override
	public List<User> get() {
		return users;
	}

}
