package com.example.demo.util;


public class Util {
	
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

}
