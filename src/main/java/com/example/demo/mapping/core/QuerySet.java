package com.example.demo.mapping.core;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.core.Model;
import com.example.demo.util.Util;

/**
 * クエリセット
 * <pre>
 * SELECT文を作成するセット
 * </pre>
 * @author secon
 */
public class QuerySet {

	public static QuerySet create(Class<? extends Model> clazz,String table,String alias) {
		return new QuerySet(clazz,table,alias);
	}

	private Class<? extends Model> clazz;
	private String tablePrefix;
	private String aliasPrefix;

	private Expression ev;
	private List<Relation> inner;
	private List<Relation> outer;

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

	public QuerySet where(Expression ev) {
		this.ev = ev;
		return this;
	}

	public QuerySet addInnerJoin(QuerySet qs,Expression ev) {
		if ( inner == null ) {
			inner = new ArrayList<>();
		}
		inner.add(new Relation(qs,ev));
		return this;
	}

	public QuerySet addOuterJoin(QuerySet qs,Expression ev) {
		if ( outer == null ) {
			inner = new ArrayList<>();
		}
		outer.add(new Relation(qs,ev));
		return this;
	}

	/**
	 * カラム名の装飾
	 * <pre>
	 * JOIN句に利用する為のカラム名を生成
	 * テーブル名.カラム名
	 * で返す。ダブルコーテーションで装飾する
	 * </pre>
	 * @param col カラム名
	 * @return テーブル名がある場合はテーブル名を付与してカラムを返す
	 */
	public String col(String col) {
		String name = col;
		name = escapeColumn(col);

		//テーブル名がない場合
		if ( Util.isEmpty(tablePrefix) ) {
			return name;
		}
		//エスケープして出力
		return String.format("\"%s\".%s",tablePrefix,name);
	}

	private static final String DQ = "\"";	

	private String escapeColumn(String v) {
		String rtn = v;
		//先頭がダブルコーテーションでない場合
		if ( rtn.indexOf(DQ) != 0 ) {
			rtn = DQ + rtn + DQ; 
		}
		return rtn;
	}
}
