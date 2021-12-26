package com.example.demo.model.query;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.ToDo;

public class ToDoMapper extends ModelMapper<List<ToDo>> {
	
	public ToDoMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<ToDo> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		ToDo todo = map.get(ToDo.class);
		list.add(todo);
	}

	@Override
	public List<ToDo> get() {
		return list;
	}
}
