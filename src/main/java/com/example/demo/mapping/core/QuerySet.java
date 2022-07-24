package com.example.demo.mapping.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.Model;
import com.example.demo.util.Util;

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
	 * WHERE句
	 */
	private Expression where = null;
	/**
	 * ORDER句
	 */
	private Order order = null;

	/**
	 * 
	 */
	private List<Relation> relations;

	
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

	/**
	 * 除外カラムを設定
	 * @return 除外するカラムの一覧
	 */
	public String[] getIgnoreColumns() {
		return ignoreColumns;
	}

	/**
	 * WHRER 句設定
	 * @param exp
	 * @return
	 */
	public QuerySet setWhere(Expression exp) {
		this.where = exp;
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
		name = SQLBuilder.escapeColumn(col);
		//テーブル名がない場合
		if ( Util.isEmpty(tablePrefix) ) {
			return name;
		}

		String t = tablePrefix;
		t = SQLBuilder.escapeColumn(t);

		//エスケープして出力
		return String.format("%s.%s",t,name);
	}

	/**
	 * SQLの出力
	 * @return
	 */
	public String toSQL() {
		
		Table tano = clazz.getAnnotation(Table.class);
		if ( tano == null ) {
			throw new RuntimeException("テーブル名の指定エラー");
		}
		
		List<Field> fields = getColumns();

		String sq = "\n";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT" + sq);
		int idx = 1;
		for ( Field f : fields ) {
			if ( idx != 1 ) {
				sql.append(",");
			}
			Column ano = f.getAnnotation(Column.class);
			if ( ano != null ) {
				sql.append(ano.value());
			}
			idx++;
		}
		sql.append(sq + "FROM " + tano.value());
		if ( where != null ) {
			sql.append(sq + "WHERE" + sq + where.toSQL());
		}

		if ( order != null ) {
			sql.append(sq + "ORDER BY" + sq + order.toSQL());
		}
		
		return sql.toString();
	}

	/**
	 * 取得用のカラム名一覧
	 * @return
	 */
	public String getColumnNames() {
		
		String model = getTablePrefix();
		String as = getAliasPrefix();

		List<Field> columns = getColumns();
		StringBuffer buf = new StringBuffer();
		int idx = 1;
		for ( Field field : columns ) {
			Column col = field.getAnnotation(Column.class);
			MappingName map = field.getAnnotation(MappingName.class);
			
			String colName = col.value();
			String rename = map.value();
			String comma = ", ";

			//最後の場合
			if ( idx == columns.size() ) {
				comma = "";
			}
			String cols = escape(model , colName,false) + " AS " + 
			              escape(as,rename,true) + comma;
			buf.append(cols);
			idx++;
		}
		return buf.toString();
	}

	/**
	 * カラムの一覧を取得
	 * @return カラムフィールドを取得
	 */
	public List<Field> getColumns() {

		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<>();
		String[] ignores = getIgnoreColumns();

		for ( Field f : fields ) {
			Column ano = f.getAnnotation(Column.class);
			if ( ano != null ) {
				if ( Util.exists(ignores,ano.value()) ) {
					continue;
				}
				list.add(f);
			}
		}
		return list;
	}
	
	/**
	 * SQLエスケープ文字挿入
	 * <pre>
	 * prefixが存在しない場合にSQLのエスケープを入れておく
	 * </pre>
	 * @param prefix テーブル名(もしくは別名)
	 * @param colName カラム名
	 * @param force 強制エスケープ
	 * @return エスケープした文字列
	 */
	private String escape(String prefix, String colName, boolean force) {

		if ( force ) {
			String esc = colName;
			if ( !Util.isEmpty(prefix) ) {
				esc = String.format("%s.%s", prefix,colName);
			}
			return SQLBuilder.escapeColumn(esc);
		} else {
			String col = SQLBuilder.escapeColumn(colName);
			if ( !Util.isEmpty(prefix) ) {
				return String.format("%s.%s", SQLBuilder.escapeColumn(prefix), col);
			} else {
				return col;
			}
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Object[] values() {
		if (where != null ) {
			return where.values();
		}
		return null;
	}

	public void addJoin(Join join, QuerySet qs, Expression exp) {
		Relation rel = new Relation(join,qs,exp);
		if ( relations == null ) {
			relations = new ArrayList<>();
		}
		relations.add(rel);
	}

	public List<Relation> getRelations() {
		return relations;
	}
}
