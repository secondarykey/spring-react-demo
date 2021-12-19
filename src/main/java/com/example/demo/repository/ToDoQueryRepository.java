package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ToDo;

@Repository
public class ToDoQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public ToDoQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<ToDo> findAll() {
		String sql = "SELECT * from TODOS";
		return super.select(ToDo.class, sql);
	}
}
