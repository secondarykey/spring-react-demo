package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Operation;
import com.example.demo.model.OperationLanguage;

public class OperationMapper extends ModelMapper<List<Operation>> {
	
	public OperationMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Operation> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Operation ope = map.get(Operation.class);
		OperationLanguage lang = map.get(OperationLanguage.class);
		if ( lang != null ) {
			ope.setName(lang.getName());
		}
		list.add(ope);
	}

	@Override
	public List<Operation> get() {
		return list;
	}
}
