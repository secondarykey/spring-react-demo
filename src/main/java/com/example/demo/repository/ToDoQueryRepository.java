package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Todos;

@Repository
public class ToDoQueryRepository extends QueryRepository {

	@Autowired(required=true)
	public ToDoQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Todos> findAll() {

		String sql = "SELECT %s FROM TODOS";

		QuerySet qs = QuerySet.create(Todos.class,"", "");
		SQLBuilder builder = SQLBuilder.create(qs);
		builder.setSQL(sql);
		
		List<Row> rows = this.query(builder);
		List<Todos> list = new ArrayList<>();
		for ( Row row : rows ) {
			list.add(row.get(qs));
		}
		return list;
	}
}
