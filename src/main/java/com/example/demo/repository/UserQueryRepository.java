package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.query.SQLBuilder;
import com.example.demo.model.query.QuerySet;
import com.example.demo.model.query.UserRoleMapper;
import com.example.demo.util.EncryptUtil;

@Repository
public class UserQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserQueryRepository.class);

	@Autowired(required=true)
	public UserQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public User findByPassword(String id, String password) {
		String sql = """
		SELECT %s FROM USERS WHERE "ID" = ? AND "PASSWORD" = ?
				""";
		SQLBuilder builder = SQLBuilder.create(
				QuerySet.create(User.class,"", "")
		);
		builder.setSQL(sql, id,EncryptUtil.hashPassword(password));

		UserRoleMapper mapper = new UserRoleMapper(builder);
		this.query(mapper);
		return mapper.get();
	}

	public User joinRole(String id) {

		String sql = """
		SELECT 
		    %s
		FROM USERS U JOIN ROLE R ON U.ROLE = R.ID AND U.ID = ?
				""";

		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(User.class,"U","users"),
			QuerySet.create(Role.class,"R","roles")
		);
		builder.setSQL(sql,id);

		UserRoleMapper mapper = new UserRoleMapper(builder);
		this.query(mapper);
		return mapper.get();
	}
}
