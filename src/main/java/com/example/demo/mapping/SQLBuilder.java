package com.example.demo.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.mapping.Column;

import com.example.demo.anotation.model.MappingRS;
import com.example.demo.model.Model;
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
	private String order;
	
	/**
	 * SQL文の取得
	 * @return 発行するSQL
	 */
	public String getSQL() {
		if ( !Util.isEmpty(order) ) {
			sql += " " + order;
		}
		if ( paging != null ) {
			sql += " LIMIT ? OFFSET ?";
		}
		return sql;
	}

	/**
	 * 引数の取得
	 * @return 渡した引数
	 */
	public Object[] getArgs() {
		if ( paging != null ) {
			Object[] rtn = new Object[args.length + 2];
			System.arraycopy(args, 0, rtn, 0, args.length);
			rtn[args.length] = paging.getNumberOfDisplay();
			
			rtn[args.length + 1] = paging.getOffset();

			return rtn;
		}
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
			String line = SQLBuilder.generateColumns(set.getModelClass(), 
					set.getTablePrefix(), set.getAliasPrefix());
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
	 * 別名を設定したカラム名のCSVを出力
	 * <pre>
	 * SQLで別名をつける場合に使用
	 *  "SELECT MappingUtil.generateColumn(Model.class,"PLAN","plan") FROM PLAN"
	 * というSQLを発行した場合、Modelにつけたアノテーションから
	 *  "SELECT PLAN.ID AS plan.id ,,,, FROM PLAN"
	 *  というSQLに変換してくれる。
	 * create() から、モデルを生成できる。
	 * </pre>
	 * @param clazz
	 * @param model
	 * @param as
	 * @return
	 */
	public static String generateColumns(Class<? extends Model> clazz,String model,String as) {
		
		List<Field> columns = SQLBuilder.getColumns(clazz);
		StringBuffer buf = new StringBuffer();
		int idx = 1;
		for ( Field field : columns ) {
			Column col = field.getAnnotation(Column.class);
			MappingRS map = field.getAnnotation(MappingRS.class);
			
			String colName = col.value();
			String rename = map.value();
			String comma = ", ";
			
			//最後の場合
			if ( idx == columns.size() ) {
				comma = "";
			}
			String cols = escape(model , colName,false) + " AS " + escape(as,rename,true) + comma;
			buf.append(cols);
			idx++;
		}
		return buf.toString();
	}

	/**
	 * SQLエスケープ文字挿入
	 * <pre>
	 * prefixが存在しない場合にSQLのエスケープを入れておく
	 * </pre>
	 * @param prefix テーブル名(もしくは別名)
	 * @param colName カラム名
	 * @param force 強制エスケープ
	 * @return
	 */
	private static String escape(String prefix, String colName, boolean force) {

		if ( force ) {
			String esc = colName;
			if ( !Util.isEmpty(prefix) ) {
				esc = String.format("%s.%s", prefix,colName);
			}
			return "\"" + esc + "\"";
		} else {
			if ( !Util.isEmpty(prefix) ) {
				return String.format("\"%s\".\"%s\"", prefix,colName);
			} else {
				return String.format("\"%s\"", colName);
			}
		}
	}

	/**
	 * マッピングモデル生成
	 * <pre>
	 * @param <T>
	 * @param clazz 生成クラス
	 * @param prefix SQLで使用したオブジェクトアクセスのPrefix
	 * @param rs SQLの戻り値
	 * @return 生成したT
	 */
	public static <T extends Model> T create(Class<T> clazz,String prefix,ResultSet rs) {
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

		List<Field> columns = SQLBuilder.getColumns(clazz);
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
	 * @param setter セッター
	 * @param model セッターの存在するオブジェクト
	 * @param rs データセット
	 * @param field フィールド
	 * @param name 
	 */
	private static void callSetter(Object  model, Field field, String prefix,ResultSet rs) {
		//MappingRSを取得
		MappingRS map = field.getAnnotation(MappingRS.class);
		Class<?> tClazz = field.getType();

		String val = map.value();
		String method = map.method();
		String name = val;
		if ( !Util.isEmpty(prefix) ) {
			name = String.format("%s.%s", prefix,name);
		}
		String setterName = "set" + Util.capitalize(val);

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
			if ( !Util.isEmpty(method) ) {
				Method getMethod;
				try {
					getMethod = rs.getClass().getMethod(method, String.class);
				} catch (NoSuchMethodException | SecurityException e) {
					throw new RuntimeException("ResultSet.getterの取得失敗",e);
				}
				Object obj = getMethod.invoke(rs, name);
				logger.debug("getObject = {}",obj.getClass());
				
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
				} else {
					setter.invoke(model, rs.getObject(name,tClazz) );
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("データ取得時の例外",e);
		} catch (IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("メソッド呼び出し時の例外",e);
		}
		return;
	}

	/**
	 * Column定義のフィールドを取得
	 * @param claza 対象クラス
	 * @return Column定義のフィールド
	 */
	private static List<Field> getColumns(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<>();
		for ( Field f : fields ) {
			Column ano = f.getAnnotation(Column.class);
			if ( ano != null ) {
				list.add(f);
			}
		}
		return list;
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
	 * @return
	 */
	public String getCountSQL() {
		return this.argSQL.formatted("COUNT(*)");
	}

	/**
	 * ページデータの設定
	 * @param paging
	 */
	public void setPaging(Paging paging) {
		this.paging = paging;
	}

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

	public void setOrder(String string) {
		this.order = string;
	}
}