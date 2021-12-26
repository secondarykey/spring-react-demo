package com.example.demo.model.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.Model;

public abstract class ModelMapper<T> implements RowCallbackHandler {

	private SQLBuilder builder;
	public ModelMapper(SQLBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		List<QuerySet> sets = builder.getQuerySets();
		MappingObject map = new MappingObject();
		for ( QuerySet set : sets ) {
			Class<? extends Model> clazz = set.getModelClass();
			Model t = SQLBuilder.create(clazz, set.getAliasPrefix(), rs);
			map.add(clazz, t);
		}
		mapping(map);
	}

	protected abstract void mapping(MappingObject map);

	public abstract T get();

	public String getSQL() {
		return builder.getSql();
	}
	public Object[] getArguments() {
		return builder.getArgs();
	}	
	
	protected class MappingObject {
		private Map<Class<? extends Model>,Model> map = new HashMap<>();
		public void add(Class<? extends Model> clazz,Model model) {
			map.put(clazz, model);
		}
		@SuppressWarnings({ "hiding", "unchecked" })
		public<T extends Model> T get(Class<T> clazz) {
			return (T)map.get(clazz);
		}
	}
}
