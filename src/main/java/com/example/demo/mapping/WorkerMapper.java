package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.User;
import com.example.demo.model.Worker;

public class WorkerMapper extends ModelMapper<List<Worker>> {
	
	public WorkerMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Worker> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Worker work = map.get(Worker.class);
		User user = map.get(User.class);
		if ( user != null ) {
			String name = user.getName();
			work.setName(name);
		}
		list.add(work);
	}

	@Override
	public List<Worker> get() {
		return list;
	}
}
