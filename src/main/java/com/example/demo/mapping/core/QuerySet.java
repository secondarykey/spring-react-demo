package com.example.demo.mapping.core;

import com.example.demo.model.core.Model;

/**
 * クエリセット
 * <pre>
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
	
}
