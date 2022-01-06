package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Time;

public class TimeMapper extends ModelMapper<List<Time>> {
	
	public TimeMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Time> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Time time = map.get(Time.class);
		list.add(time);
	}

	@Override
	public List<Time> get() {
		return list;
	}
}
