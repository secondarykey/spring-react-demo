package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
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
		String sql = "SELECT * FROM USERS WHERE ID = ? AND PASSWORD = ?";
		return this.get(User.class,sql,id,EncryptUtil.hashPassword(password));
	}

	public User joinRole(String id) {
		String sql = "SELECT U.*,R.NAME AS ROLENAME FROM USERS U JOIN Role R ON U.ROLE = R.ID AND U.ID = ?";
		return this.get(User.class,sql,id);
	}

	@Transactional
	public int updatePassword(User user) {
		String sql = "UPDATE USERS SET PASSWORD = ?, EXPIRY = ? WHERE ID = ?";
		return this.update(sql,EncryptUtil.hashPassword(user.getPassword()),user.getExpiry(),user.getId());
	}

}
