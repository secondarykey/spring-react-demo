package com.example.demo.mapping.core;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.core.Model;
import com.example.demo.util.Util;

/**
 * クエリセット
 * <pre>
<<<<<<< HEAD
 * 取得するSQLで、名称とオブジェクトを関連付けするセット
 * ・クラス名
 * ・SQL内の名称(SQL内でAS句で指定した値)
 * ・別名（JDBCで取得する為に設定する名称）
 * をセットで使用します。
 * 名称は空で動作しますが、あくまで他のテーブルイメージ、名称を付けなかった場合です。
 * 別名も空で動作しますが、同一クラス、カラム名で解決できない場合に取得できなくなるため、
 * 付けておいた方がよいです。
 * </pre>
 * @author p230985
 */
public class QuerySet {

	/**
	 * クエリセットの生成
	 * @param clazz 指定クラス
	 * @param table テーブル名
	 * @param alias 別名
	 * @return クエリセット
	 */
	public static QuerySet create(Class<? extends Model> clazz,String table,String alias) {
		return new QuerySet(clazz,table,alias);
	}
	
	/**
	 * マッピングクラス
	 */
	private Class<? extends Model> clazz;
	
	/**
	 * SQL名称
	 */
	private String tablePrefix;
	/**
	 * JDBC名称
	 */
	private String aliasPrefix;
	
	private Expression where;
	private List<Relation> inner;
	private List<Relation> outer;

	/**
	 * 除外するカラム名
	 */
	private String[] ignoreColumns;
	
	/**
	 * コンストラクタ
	 * @param clazz モデルクラス
	 * @param table テーブル名
	 * @param alias 別名
	 */
	private QuerySet(Class<? extends Model> clazz,String table,String alias) {
		this.clazz = clazz;
		this.tablePrefix = table;
		this.aliasPrefix = alias;
	}
	
	/**
	 * テーブルの名称
	 * @return テーブル名
	 */
	public String getTablePrefix() {
		return tablePrefix;
	}
	
	/**
	 * JDBCで使用する名称の取得
	 * @return JDBCで使用する名称
	 */
	public String getAliasPrefix() {
		return aliasPrefix;
	}
	
	/**
	 * モデルクラスを取得
	 * @return 設定したクラス
	 */
	public Class<? extends Model> getModelClass() {
		return clazz;
	}

	/**
	 * 除外カラムの設定
	 * @param clms 除外するカラム名
	 */
	public void ignoreColumns(String... clms) {
		ignoreColumns = clms;
	}
	
	public String[] getIgnoreColumns() {
		return ignoreColumns;
	}
	
	public QuerySet where(Expression exp) {
		this.where = exp;
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
			outer = new ArrayList<>();
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
