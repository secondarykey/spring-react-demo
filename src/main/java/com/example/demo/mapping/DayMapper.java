package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Day;

public class DayMapper extends ModelMapper<List<Day>> {
	
	public DayMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Day> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Day day = map.get(Day.class);
		list.add(day);
	}

	@Override
	public List<Day> get() {
		return list;
	}
}
