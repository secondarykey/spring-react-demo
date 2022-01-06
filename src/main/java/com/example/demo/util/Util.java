package com.example.demo.util;

import java.util.List;

public class Util {

	public static String capitalize(String value) {
		if ( Util.isEmpty(value) ) {
			return "";
		}
		String p = value.substring(0,1);
		String tail = value.substring(1);
		return p.toUpperCase() + tail;
	}	

	public static boolean isEmpty(String val) {
		if ( val == null ) return true;
		return val.isEmpty();
	}

	@SuppressWarnings("unused")
	public static int count(Iterable<?> itr) {
		if ( itr == null ) {
			return 0;
		}
		int cnt = 0;
		for ( Object obj : itr ) {
			cnt++;
		}
		return cnt;
	}

	public static boolean isEmpty(List<?> messages) {
		if ( messages == null || messages.size() == 0 ) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String[] args) {
		if ( args == null || args.length == 0 ) {
			return true;
		}
		return false;
	}

	public static boolean equals(String v1, String v2) {
		if ( v1 == null ) {
			if ( v2 == null ) {
				return true;
			}
			return false;
		}

		if ( v1.equals(v2) ) {
			return true;
		}
		return false;
	}

	/**
	 * デフォルト値判定
	 * <pre>
	 * 引数の値がデフォルトかを判定
	 * 現状言語設定のみ
	 * 空文字もデフォルトにする。
	 * </pre>
	 * @param v 設定値
	 * @return true時デフォルト
	 */
	public static boolean isDefault(String v) {
		if ( Util.isEmpty(v) ) {
			return true;
		}
		if ( Util.equals(v, "default") ) {
			return true;
		}
		return false;
	}
}
