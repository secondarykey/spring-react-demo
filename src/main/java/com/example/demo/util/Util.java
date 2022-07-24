package com.example.demo.util;

import java.util.List;

public class Util {

	/**
	 * 先頭文字を大文字にする
	 * <pre>
	 * </pre>
	 * @param value
	 * @return
	 */
	public static String capitalize(String value,boolean toLower) {

		if ( Util.isEmpty(value) ) {
			return "";
		}
		String v = value;
		if ( toLower ) {
			v = value.toLowerCase();
		}
		String p = v.substring(0,1);
		String tail = v.substring(1);
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
	
	/**
	 * 文字列存在確認
	 * @param ignores 拒否する配列
	 * @param value 対象の値
	 * @return 存在する場合true
	 */
	public static boolean exists(String[] ignores, String value) {
		//対象が空の場合false
		if ( Util.isEmpty(ignores) ) {
			return false;
		}
		for ( String v : ignores ) {
			if ( Util.equals(v,value) )  {
				return true;
			}
		}
		return false;
	}

	/**
	 * オブジェクト配列から、オブジェクト配列を生成
	 * @param arrays オブジェクト配列群
	 * @return オブジェクト配列
	 */
	public static Object[] newArray(Object[] ... arrays) {

		int leng = 0;
		for ( Object[] array : arrays ) {
			leng += array.length;
		}
		Object[] newArray = new Object[leng];

		int idx = 0;
		for ( Object[] array : arrays ) {
			int l = array.length;
			if ( l != 0 ) {
				System.arraycopy(array, 0, newArray, idx, l);
				idx += l;
			}
		}
		return newArray;
	}
	
	
}
