package com.example.demo.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD
})

/**
 * カラム名称
 * <pre>
 * JDBCが付ける別名
 * メソッド名を指定すると、JDBC上から取得する際の呼び出しを指定できる
 * </pre>
 * @author p230985
 */
public @interface MappingName {
	/**
	 * 値の取得
	 * @return 値
	 */
	String value();
	
	/**
	 * メソッド名
	 * @return メソッド名
	 */
	String method() default "";
}
