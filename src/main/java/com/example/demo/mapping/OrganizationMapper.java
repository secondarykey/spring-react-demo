package com.example.demo.mapping;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Organization;

public class OrganizationMapper extends ModelMapper<List<Organization>> {
	
	public OrganizationMapper(SQLBuilder builder) {
		super(builder);
	}

	private List<Organization> list = new ArrayList<>();

	@Override
	protected void mapping(MappingObject map) {
		Organization day = map.get(Organization.class);
		list.add(day);
	}

	@Override
	public List<Organization> get() {
		return list;
	}
}
