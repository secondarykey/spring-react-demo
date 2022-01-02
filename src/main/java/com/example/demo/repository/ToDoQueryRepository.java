package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.QuerySet;
import com.example.demo.mapping.SQLBuilder;
import com.example.demo.mapping.ToDoMapper;
import com.example.demo.model.ToDo;

@Repository
public class ToDoQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public ToDoQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<ToDo> findAll() {
		String sql = "SELECT %s FROM TODOS";
		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(ToDo.class,"", "")
		);
		builder.setSQL(sql);
		
		ToDoMapper mapper = new ToDoMapper(builder);
		this.query(mapper);
		return mapper.get(); 
	}
}
