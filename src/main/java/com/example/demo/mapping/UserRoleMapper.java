package com.example.demo.mapping;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public class UserRoleMapper extends ModelMapper<User> {

	public UserRoleMapper(SQLBuilder builder) {
		super(builder);
	}

	private User user;
	@Override
	protected void mapping(MappingObject map) {
		User user = map.get(User.class);
		Role role = map.get(Role.class);
		user.setRoleObj(role);
		this.user = user;
	}

	@Override
	public User get() {
		return user;
	}

}
