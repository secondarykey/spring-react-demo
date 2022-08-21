package com.example.demo.util;

/**
 * クラス関連のユーティリティ
 * @author secon
 */
public class ClassUtil {

	/**
	 * プリミティブが同一の型同士かを比較
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static boolean same(Class<?> clazz1, Class<?> clazz2) {
		if ( isInteger(clazz1) ) {
			if ( isInteger(clazz2) ) return true;
			return false;
		} else if ( isDouble(clazz1) ) {
			if ( isDouble(clazz2) ) return true;
			return false;
		} else if ( isByte(clazz1) ) {
			if ( isByte(clazz2) ) return true;
			return false;
		} else if ( isLong(clazz1) ) {
			if ( isLong(clazz2) ) return true;
			return false;
		} else if ( isShort(clazz1) ) {
			if ( isShort(clazz2) ) return true;
			return false;
		} else if ( isFloat(clazz1) ) {
			if ( isFloat(clazz2) ) return true;
			return false;
		} else if ( isChar(clazz1) ) {
			if ( isChar(clazz2) ) return true;
			return false;
		}
		return false;
	}
	
	/**
	 * Integer型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isInteger(Class<?> clazz) {
		if ( clazz == int.class ) {
			return true;
		} else if ( clazz == Integer.class ) {
			return true;
		}
		return false;
	}

	/***
	 * Double型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isDouble(Class<?> clazz) {
		if ( clazz == double.class ) {
			return true;
		} else if ( clazz == Double.class ) {
			return true;
		}
		return false;
	}

	/**
	 * Byte型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isByte(Class<?> clazz) {
		if ( clazz == byte.class ) {
			return true;
		} else if ( clazz == Byte.class ) {
			return true;
		}
		return false;
	}

	/**
	 * Short型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isShort(Class<?> clazz) {
		if ( clazz == short.class ) {
			return true;
		} else if ( clazz == Short.class ) {
			return true;
		}
		return false;
	}

	/**
	 * Long型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isLong(Class<?> clazz) {
		if ( clazz == long.class ) {
			return true;
		} else if ( clazz == Long.class ) {
			return true;
		}
		return false;
	}

	/**
	 * Float型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isFloat(Class<?> clazz) {
		if ( clazz == float.class ) {
			return true;
		} else if ( clazz == Float.class ) {
			return true;
		}
		return false;
	}

	/**
	 * Char型判定
	 * @param clazz
	 * @return
	 */
	public static Boolean isChar(Class<?> clazz) {
		if ( clazz == char.class ) {
			return true;
		} else if ( clazz == Character.class ) {
			return true;
		}
		return false;
	}
}
