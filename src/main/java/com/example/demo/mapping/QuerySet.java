package com.example.demo.mapping;

import com.example.demo.model.Model;

public class QuerySet {

	public static QuerySet create(Class<? extends Model> clazz,String table,String alias) {
		return new QuerySet(clazz,table,alias);
	}
	private Class<? extends Model> clazz;
	private String tablePrefix;
	private String aliasPrefix;
	public QuerySet(Class<? extends Model> clazz,String table,String alias) {
		this.clazz = clazz;
		this.tablePrefix = table;
		this.aliasPrefix = alias;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public String getAliasPrefix() {
		return aliasPrefix;
	}
	public Class<? extends Model> getModelClass() {
		return clazz;
	}
}
