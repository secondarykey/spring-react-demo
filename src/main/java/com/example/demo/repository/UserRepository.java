package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;

@Repository
public class UserRepository extends AccessRepository {

	@Autowired(required=true)
	public UserRepository(JdbcTemplate template) {
		super(template);
	}

	public User findById(String id) {
		String sql = "SELECT * FROM USERS WHERE id = ?";
		return this.get(User.class,sql,id);
	}

	public User findByPassword(String id, String password) {
		String sql = "SELECT * FROM USERS WHERE id = ? AND password = hash('sha256', stringtoutf8(?))";
		return this.get(User.class,sql,id,password);
	}

	@Transactional
	public int updatePassword(User user) {
		//String sql = "UPDATE USERS SET password = hash('sha256', stringtoutf8(:password)), expiry = :expiry WHERE id = :id";
		String sql = "UPDATE USERS SET password = hash('sha256', stringtoutf8(?)), expiry = ? WHERE id = ?";
		return this.update(sql,user.getPassword(),user.getExpiry(),user.getId());
	}

}
