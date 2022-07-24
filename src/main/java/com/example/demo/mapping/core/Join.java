package com.example.demo.mapping.core;

public enum Join {
	LeftOuter("LEFT OUTER JOIN"),
	InnerJoin("INNER JOIN"),;

	private String sql;
	Join(String string) {
		sql = string;
	}
	public String toSQL() {
		return sql;
	}
}
