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
		String sql = "SELECT * FROM USERS WHERE id = ? AND password = md5(?)";
		return this.get(User.class,sql,id,password);
	}
	
	public User joinRole(String id) {
		String sql = "SELECT U.*,R.NAME AS ROLENAME FROM USERS U JOIN Role R ON U.ROLE = R.ID AND U.ID = ?";
		return this.get(User.class,sql,id);
	}

	@Transactional
	public int updatePassword(User user) {
		//String sql = "UPDATE USERS SET password = hash('sha256', stringtoutf8(:password)), expiry = :expiry WHERE id = :id";
		String sql = "UPDATE USERS SET password = md5(?), expiry = ? WHERE id = ?";
		return this.update(sql,user.getPassword(),user.getExpiry(),user.getId());
	}

}
