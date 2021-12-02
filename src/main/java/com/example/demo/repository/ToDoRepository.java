package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ToDo;

@Repository
public class ToDoRepository extends AccessRepository {

	@Autowired(required=true)
	public ToDoRepository(JdbcTemplate template) {
		super(template);
	}

	public List<ToDo> find() {
		String sql = "SELECT * FROM TODOS";
		return this.select(ToDo.class,sql);
	}

	@Transactional
	public void insert(ToDo todo) {
		String sql = "INSERT INTO TODOS(VALUE) VALUES (?)";
		Number num = this.insert(sql,todo.getValue());
		todo.setId(num.intValue());
	}

	@Transactional
	public int delete(ToDo todo) {
		String sql = "DELETE FROM TODOS WHERE id = ?";
		return this.update(sql,todo.getId());
	}
}
