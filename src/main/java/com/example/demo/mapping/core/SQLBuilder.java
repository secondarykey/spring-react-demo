package com.example.demo.mapping.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.annotation.MappingName;
import com.example.demo.model.core.Model;
import com.example.demo.repository.core.QueryRepository;
import com.example.demo.transfer.Paging;
import com.example.demo.util.Util;

/**
 * SQL作成
 * <pre>
 * SQL生成を行います。
 * 
 * SELECT %s FROM ....
 * 
 * 
 * 
 * 
 * </pre>
 */
public class SQLBuilder {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SQLBuilder.class);

	/**
	 * オブジェクト生成
	 * @param querySets SQLのカラムを構築する
	 * @return
	 */
	public static SQLBuilder create(QuerySet... querySets) {
		SQLBuilder builder = new SQLBuilder();
		for ( QuerySet set : querySets ) {
			builder.addQuerySet(set);
		}
		return builder;
	}

	/**
	 * クエリセット
	 */
	private List<QuerySet> sets = new ArrayList<>();
	
	/**
	 * 発行SQL
	 */
	private String sql;
	
	/**
	 * 元SQL
	 */
	private String argSQL;
	/**
	 * 引数配列
	 */
	private Object[] args;

	/**
	 * ページング
	 */
	private Paging paging;

	/**
	 * ORDER 
	 */
	@Deprecated
	private String orderClms;

	/**
	 * 
	 */
	private Order order;

	/**
	 * SQL文の取得
	 * @return 発行するSQL
	 */
	public String getSQL() {
		if ( order != null ) {
			
			sql += " ORDER BY " + order.toSQL();
			
		} else {
			if ( !Util.isEmpty(orderClms) ) {
				sql += " " + orderClms;
			} 
		}

		if ( paging != null ) {
			int limit = paging.getNumberOfDisplay();
			int offset = paging.getOffset();
			sql += String.format(" LIMIT ? OFFSET ?",limit,offset);
		}
		return sql;
	}

	/**
	 * 引数の取得
	 * @return 渡した引数
	 */
	public Object[] getArgs() {
		return args;
	}

	public Object[] getCountArgs() {
		return args;
	}

	/**
	 * クエリセットの追加
	 * @param set クエリセット
	 */
	private void addQuerySet(QuerySet set) {
		sets.add(set);
	}

	/**
	 * SQLの作成
	 * @param sql 元SQL
	 * @param arguments
	 * @return 発行SQL
	 */
	public String setSQL(String sql,Object... arguments) {

		StringBuffer buf = new StringBuffer();
		for ( QuerySet set : sets ) {
			if ( buf.length() != 0 ) {
				buf.append(",");
			}
			String line = set.getColumnNames();
			buf.append(line);
		}

		String clm = buf.toString();
		String clmSql = String.format(sql, clm);

		this.argSQL = sql;
		this.sql = clmSql;
		this.args = arguments;

		return clmSql;
	}

	/**
	 * QuerySetの取得
	 * @return
	 */
	public List<QuerySet> getQuerySets() {
		return sets;
	}
	
	/**
	 * マッピングモデル生成
	 * <pre>
	 * 指定したクラスでマッピングオブジェクトを生成
	 * </pre>
	 * @param <T> 指定クラス
	 * @param clazz 生成クラス
	 * @param qs 対象のクエリセット
	 * @param rs SQLの戻り値
	 * @return 生成したT
	 */
	public static <T extends Model> T create(Class<T> clazz,QuerySet qs,ResultSet rs) {
		
		String prefix = qs.getAliasPrefix();
		Constructor<T> con;
		try {
			con = clazz.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("コンストラクタにアクセス不可",e);
		}

		T model;
		try {
			model = con.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("デフォルトコンストラクタでの生成エラー",e);
		}

		List<Field> columns = qs.getColumns();
		for ( Field field : columns ) {
			callSetter(model,field,prefix,rs);
		}
		return model;
	}

	/**
	 * モデルデータの設定を行う
	 * <pre>
	 * モデルのセッターにResultSetから設定を行う
	 * 
	 * </pre>
	 * @param model セッターの存在するオブジェクト
	 * @param field フィールド
	 * @param prefix 接頭子
	 * @param rs データセット
	 */
	private static void callSetter(Object model, Field field, String prefix,ResultSet rs) {

		//MappingRSを取得
		MappingName map = field.getAnnotation(MappingName.class);
		Class<?> tClazz = field.getType();

		String val = map.value();
		String method = map.method();
		String name = val;
		if ( !Util.isEmpty(prefix) ) {
			name = String.format("%s.%s", prefix,name);
		}
		String setterName = "set" + Util.capitalize(val,false);

		Method setter;
		try {
			setter = model.getClass().getMethod(setterName, tClazz);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("メソッド生成エラー:" + setterName,e);
		}
	
		if ( logger.isDebugEnabled() ) {
			logger.debug("Class:{} SetterName :{}",tClazz.getName(),setter.getName());
		}
		try {
			
			//メソッド指定の場合
			if ( !Util.isEmpty(method) ) {

				boolean usingCal = false;
				Method getMethod;
				try {
					if ( Util.equals(method,"getTime") || Util.equals(method,"getDate") ) {
						usingCal = true;
						getMethod = rs.getClass().getMethod(method, String.class,Calendar.class);
					} else {
						getMethod = rs.getClass().getMethod(method, String.class);
					}
				} catch (NoSuchMethodException | SecurityException e) {
					throw new RuntimeException("ResultSet.getterの取得失敗",e);
				}

				Object obj = null;
				if ( usingCal ) {
					obj = getMethod.invoke(rs, name,Calendar.getInstance());
				} else {
					obj = getMethod.invoke(rs, name);
				}
				if ( obj != null ) {
					logger.debug("getObject = {}",obj.getClass());
				}
				setter.invoke(model,obj);

			} else {
				
				Object obj = rs.getObject(name);
				if ( obj != null && logger.isDebugEnabled() ) {
					logger.debug("Object Class[{}], [{}]",obj.getClass() , tClazz.getName());
				}

				//プリミティブ型は呼び出しエラーになる
				if ( tClazz == int.class ) {
					setter.invoke(model, rs.getInt(name));
				} else if ( tClazz == Double.class ) {
					setter.invoke(model, rs.getDouble(name));
				} else if ( tClazz == Long.class ) {
					setter.invoke(model, rs.getLong(name));
				} else if ( tClazz == Boolean.class ) {
					setter.invoke(model, rs.getBoolean(name));
				} else if ( tClazz == byte[].class ) {
					InputStream is = rs.getBinaryStream(name);
					byte[] bytes;
					try {
						bytes = getBytes(is);
					} catch (IOException e) {
						throw new RuntimeException("Blobデータの取り出しに失敗",e);
					}
					setter.invoke(model, bytes);
				} else {
					setter.invoke(model, rs.getObject(name,tClazz) );
				}
			}
		} catch (SQLException e) {
			logger.info("Model:{},Name:{},Class:{}",model,name,tClazz);
			throw new RuntimeException("データ取得時の例外",e);
		} catch (IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("メソッド呼び出し時の例外",e);
		}
		return;
	}

	private static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[1024 * 256];
        while ((read = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, read);
        }
        is.close();
        return buffer.toByteArray();
	}

	/**
	 * 元のSQL
	 * <pre>
	 * 構築時のSQL
	 * </pre>
	 * @return
	 */
	public String getArgSQL() {
		return argSQL;
	}

	/**
	 * カウント文の取得
	 * @return カウント湯のカラム
	 */
	public String getCountSQL() {
		return this.argSQL.formatted("COUNT(*)");
	}

	/**
	 * ページデータの設定
	 * @param paging ページデータ
	 */
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	/**
	 * カウント値の設定
	 * @param cnt 設定件数
	 */
	public void setCount(int cnt) {
		if (this.paging == null) {
			logger.warn("ページングがない状態でカウントを発行 {}",cnt);
			return;
		}
		logger.info("行数:{}",cnt);
		this.paging.setDbCount(cnt);
	}

	public boolean isPaging() {
		return this.paging != null;
	}

	/**
	 * 行取得
	 * @param repo
	 * @return
	 */
	public List<Row> getRows(QueryRepository repo) {

		if ( sets.size() != 1 ) {
			throw new RuntimeException("QuerySetの複数件はサポートしていません。");
		}
		//TODO ページング指定時の考慮

		QuerySet set = sets.get(0);
		
		String cols = set.getColumnNames();
		String from = set.toSQL();

		StringBuffer sql = new StringBuffer();
		sql.append(String.format("SELECT \n%s\n", cols));
		sql.append(String.format("FROM \n(%s)", from));
		if ( !Util.isEmpty(set.getTablePrefix()) ) {
			sql.append(String.format(" AS %s", escapeColumn(set.getTablePrefix())));
		}

		Object[] vals = set.values();

		//List<Relation> inner = set.getInner();
		List<Relation> relations = set.getRelations();
		if ( relations != null ) {
			for ( Relation outer : relations ) {
				Join join = outer.getJoin();
				QuerySet qs = outer.getQs();
				Expression ex = outer.getExpression();
				sql.append("\n" + join.toSQL() + "\n(");
				sql.append(qs.toSQL());
				sql.append(") AS " + escapeColumn(qs.getTablePrefix()));
				sql.append("\nON\n");
				sql.append(ex.toSQL());
				vals = Util.newArray(vals,qs.values());
			}
		}

		//TODO リミットを設定
		//TODO ORDERを設定
		
		QueryMapper mapper = new QueryMapper(set);
	
		logger.info(sql.toString());
		
		repo.query(sql.toString(),vals,mapper);
		return mapper.result();
	}

	/**
	 * オーダー句のセット
	 * @param strings
	 */
	public void setOrder(String strings) {
		orderClms = strings;
	}
	
	//以下新I/F用の設定
	public static final String DQ = "\"";	

	/**
	 * カラムのエスケープ
	 * <pre>
	 * 文字列をダブルコーテーションで囲む
	 * 最初がダブルコーテーションの場合、処理しない
	 * 
	 * AAA.BBB のような文字列も"AAA.BBB"と囲むので注意
	 * </pre>
	 * @param v 対象文字列
	 * @return ダブルコーテーションで囲んだ文字列
	 */
	public static String escapeColumn(String v) {
		String rtn = v;
		//先頭がダブルコーテーションでない場合
		if ( rtn.indexOf(DQ) != 0 ) {
			rtn = DQ + rtn + DQ; 
		}
		return rtn;
	}


}
