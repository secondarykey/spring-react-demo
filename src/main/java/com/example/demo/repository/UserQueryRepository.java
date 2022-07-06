package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
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

	public Users findByPassword(String id, String password) {
		String sql = """
		SELECT %s FROM USERS WHERE "ID" = ? AND "PASSWORD" = ?
				""";
		QuerySet qs = QuerySet.create(Users.class,"", "");
		SQLBuilder builder = SQLBuilder.create(qs);
		builder.setSQL(sql, id,EncryptUtil.hashPassword(password));
		
		Row row = singleQuery(builder);
		return row.get(qs);
	}

	public Users joinRole(String id) {

		String sql = """
		SELECT 
		    %s
		FROM USERS U JOIN ROLE R ON U.ROLE = R.ID AND U.ID = ?
				""";

		QuerySet userQs = QuerySet.create(Users.class,"U","users");
		QuerySet roleQs = QuerySet.create(Role.class,"R","roles");

		SQLBuilder builder = SQLBuilder.create(userQs,roleQs);
		builder.setSQL(sql,id);

		Row row = singleQuery(builder);

		return row.get(userQs);
	}

	public List<Users> search(String id, String name, Paging paging) {

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

		QuerySet qs = QuerySet.create(Users.class,"","users");
		SQLBuilder builder = SQLBuilder.create(qs);
		
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

		List<Row> rows = this.query(builder);
		List<Users> list = new ArrayList<>();
		for (Row row:rows) {
			list.add(row.get(qs));
		}
		return list;
	}
}
