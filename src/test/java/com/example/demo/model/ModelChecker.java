package com.example.demo.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.example.demo.model.annotation.MappingRS;
import com.example.demo.util.Util;

/**
 * モデルチェッカー
 * <pre>
 * 作成したモデルのマッピング設定をチェックします。
 * 実際にSQLを発行する場合はRepositoryでチェックしてください。
 * </pre>
 */
public class ModelChecker {
	
	private final static Logger logger = LoggerFactory.getLogger(ModelChecker.class);
	
	public static boolean checkClass(Class<?> clazz) {
		ModelChecker checker = new ModelChecker(clazz);
		return checker.checkSchema();
	}

	private Class<?> clazz;
	private ModelChecker(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * スキーマのチェック
	 * <pre>
	 * ・コンストラクタの呼び出しチェック
	 * ・インスタンスの生成
	 * ・Tableアノテーションのチェック
	 * ・Tableアノテーションの大文字チェック（ワーニング）
	 * 以下はフィールド
	 * 
	 * 各エラー箇所をログに表示する
	 * </pre>
	 * @return 正しい場合true
	 */
	private boolean checkSchema() {
		//コンストラクタは引数がないか？
		Constructor<?> con;
		try {
			con = this.clazz.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("コンストラクタが存在しません。",e);
			return false;
		}

		Object inst;
		try {
			inst = con.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error("インスタンスが生成できません。",e);
			return false;
		}
		if ( inst == null ) {
			logger.error("デフォルトコンストラクタの呼び出しが不可です");
			return false;
		}

		Table table = clazz.getAnnotation(Table.class);
		if ( table == null ) {
			logger.error("Tableアノテーションが存在しません。");
			return false;
		}

		String tableName = table.value();
		if ( !Util.equals(tableName, tableName.toUpperCase()) ) {
			logger.warn("テーブル名が大文字ではありません。" + tableName);
		}

		Field[] fields = clazz.getDeclaredFields();
		
		boolean flag = false;
		boolean existId = false;
		//フィールドにColumnかTransientがあるか？
		for ( Field field : fields ) {
			Id id = field.getAnnotation(Id.class);
			if ( id != null ) {
				existId = true;
			}
			Column col = field.getAnnotation(Column.class);
			Transient tran = field.getAnnotation(Transient.class);

			if ( col == null && tran == null ) {
				logger.error("{} の {} にColumnかTransientがありません。(org.springframework.data)",inst.getClass().getName(),field.getName());
				flag = true;
			} else if ( col != null && tran != null ) {
				logger.error("{} にColumn,Transientの両方が指定されています。",inst.getClass().getName(),field.getName());
				flag = true;
			} else {
				if ( col != null ) {
					String val = col.value();
					if ( !Util.equals(val, val.toUpperCase()) ) {
						logger.warn("Column指定が大文字ではありません。",inst.getClass().getName(),field.getName());
					}

					MappingRS map = field.getAnnotation(MappingRS.class);
					if ( map == null ) {
						logger.error("{} の {} ColumnにMappingRSがありません",inst.getClass().getName(),field.getName());
						flag = true;
					}
				}
			}
		}
		
		if ( !existId ) {
			logger.warn("{} にID指定が存在しませんでした。",inst.getClass().getName());
		}

		return !flag;
	}

}
