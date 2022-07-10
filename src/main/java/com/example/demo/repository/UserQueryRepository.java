package com.example.demo.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.UserRoleMapper;
import com.example.demo.mapping.UserRowMapper;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.transfer.Paging;
import com.example.demo.util.EncryptUtil;
import com.example.demo.util.Util;

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

	public List<User> search(String id, String name, Paging paging) {

		String sql = """
		SELECT 
		    %s
		FROM USERS WHERE 1 = 1
		""";
		
		String idVal = "";
		String nameVal = "";

		if ( !Util.isEmpty(id) ) {
			sql += " AND USERS.ID LIKE ?";
			idVal = "%" + id +"%";
		}
		if ( !Util.isEmpty(name) ) {
			sql += " AND USERS.NAME LIKE ?";
			nameVal = "%" + name +"%";
		}

		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(User.class,"","users")
		);
		
		builder.setOrder("ORDER BY USERS.ID");
		builder.setPaging(paging);

		if ( Util.isEmpty(id) && Util.isEmpty(name) ) {
			builder.setSQL(sql);
		} else if ( !Util.isEmpty(id) && !Util.isEmpty(name) ) {
			builder.setSQL(sql,idVal,nameVal);
		} else if ( !Util.isEmpty(id) ) {
			builder.setSQL(sql,idVal);
		} else if ( !Util.isEmpty(name) ) {
			builder.setSQL(sql,nameVal);
		}

		UserRowMapper mapper = new UserRowMapper(builder);
		this.query(mapper);
		return mapper.get();
	}
}
