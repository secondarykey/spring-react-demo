package com.example.demo.util;

public class ClassUtil {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassT(T ...objects) {
		Cast<T> cast = new CastImpl<T>();
		return cast.getType();
	}

	private static class CastImpl<E> extends Cast<E> {
	}

	@SuppressWarnings({ "unchecked" })
	private abstract static class Cast<E> {
		private Class<E> type;
		private Cast(E... ts) {

			System.out.println(ts.getClass());

			Class<E> type = (Class<E>)ts.getClass().getComponentType();
			System.out.println(type);
			this.type = type;
		}
		public Class<E> getType() {
			return this.type;
		}
	}
}
