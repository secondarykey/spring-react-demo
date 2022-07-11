package com.example.demo.mapping.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * クエリ用式出力
 * @author secon
 */
public class Exp implements Expression {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SQLBuilder.class);

	/**
	 * 演算子
	 * @author secon
	 */
	private enum Operator {
		Eq(" = "), EqName(" = "),
		Lt(" < "), Le(" <= "),
		Gt(" > "), Ge(" >= "),
		Null(" IS NULL"),NotNull(" IS NOT NULL"),
		In(" IN ");
		private String v;
		Operator(String v) {
			this.v = v;
		}
		public String value() {
			return v;
		}
	}

	/**
	 * 演算子
	 */
	private Operator op;
	/**
	 * 左辺値（カラム名）
	 */
	private String leftValue;
	/**
	 * 右辺値
	 */
	private Object rightValue;

	/**
	 * 等価式
	 * @param clm
	 * @param v
	 * @return
	 */
	public static Exp eq(String clm,Object v) {
		return create(clm,v,Operator.Eq);
	}
	/**
	 * 等価式（カラム名）
	 * @param leftClm 左辺
	 * @param rightClm 右辺
	 * @return 生成したExp
	 */
	public static Exp eqName(String leftClm,String rightClm) {
		return create(leftClm,rightClm,Operator.EqName);
	}

	public static Exp lt(String clm,Object v) {
		return create(clm,v,Operator.Lt);
	}
	public static Exp le(String clm,Object v) {
		return create(clm,v,Operator.Le);
	}
	public static Exp gt(String clm,Object v) {
		return create(clm,v,Operator.Gt);
	}
	public static Exp ge(String clm,Object v) {
		return create(clm,v,Operator.Ge);
	}

	public static Exp nullp(String clm) {
		return create(clm,null,Operator.Null);
	}

	public static Exp notNull(String clm) {
		return create(clm,null,Operator.NotNull);
	}

	public static Exp in(String clm,List<?> v) {
		return create(clm,v,Operator.In);
	}

	public static Exp in(String clm,Object... v) {
		return create(clm,v,Operator.In);
	}
	
	/**
	 * 生成
	 * @param clm カラム名
	 * @param v 値
	 * @param op 演算子
	 * @return 式生成器
	 */
	private static Exp create(String clm,Object v,Operator op) {
		Exp ev = new Exp();
		ev.leftValue = clm;
		ev.rightValue = v;
		ev.op = op;
		return ev;
	}

	private static final String DQ = "\"";

	/**
	 * SQL出力
	 * @return SQL
	 */
	public String toSQL() {
		return this.toInnerSQL();
	}

	/**
	 * SQL出力
	 * <pre>
	 * 自分自身のSQLを出力
	 * right側を付与して全体のSQLとして出力
	 * leftOpにNOTが存在した場合、NOTで出力
	 * </pre>
	 * @return SQL
	 */
	private String toInnerSQL() {

		//カラム名をエスケープする
		String left = this.leftValue;
		if ( left != null ) {
			left = escapeColumn(left);
		}

		String sql = "";
		if ( op.equals(Operator.NotNull) || op.equals(Operator.Null) ) {
			sql = String.format("(%s%s)", left, op.value());
		} else if ( op.equals(Operator.In) ) {
			sql = String.format("(%s%s(%s))", left, op.value(),valueCSV());
		} else if ( op.equals(Operator.EqName)) {
			//カラム名指定の場合はそのまま値を利用
			sql = String.format("(%s%s%s)", left, op.value(),escapeColumn((String)rightValue));
		} else {
			sql = String.format("(%s%s%s)", left, op.value(),"?");
		}
		return sql;
	}

	private String escapeColumn(String v) {
		String rtn = v;
		//先頭がダブルコーテーションでない場合
		if ( rtn.indexOf(DQ) != 0 ) {
			rtn = DQ + rtn + DQ; 
		}
		return rtn;
	}

	/**
	 * IN句用のCSV作成
	 * @return ?のCSV
	 */
	private String valueCSV() {

		int sz = 0;
		if ( this.rightValue instanceof Collection ) {
			sz = ((Collection<?>) this.rightValue).size();
		} else if ( this.rightValue.getClass().isArray() ) {
			sz = Array.getLength(this.rightValue);
		}

		if ( sz == 0 ) {
			logger.warn("in() value is zero.{}",this.leftValue);
		}

		//? のCSV作成
		StringBuffer buf = new StringBuffer();
		for ( int idx = 0; idx < sz; idx++ ) {
			if ( buf.length() != 0 ) {
				buf.append(",");
			}
			buf.append("?");
		}
		return buf.toString();
	}

	/**
	 * AND作成
	 * @param right 右辺
	 * @return ANDで生成したExp
	 */
	public Expression and(Expression right) {
		return Formula.and(this,right);
	}

	/**
	 * 
	 * @param ev
	 * @return
	 */
	public Expression or(Expression right) {
		return Formula.or(this,right);
	}

	/**
	 * 
	 * @return
	 */
	public Expression not() {
		return Formula.not(this);
	}

	/**
	 * 値の取得
	 * @return 
	 */
	public Object[] values() {
		List<Object> list = new ArrayList<>();
		if ( this.rightValue == null ) {
			logger.warn("right value is null[{}]",this.leftValue);
		} else {
			if ( this.rightValue instanceof Collection ) {
				for ( Object elm : (Collection<?>)this.rightValue ) {
					list.add(elm);
				}
			} else if ( this.rightValue.getClass().isArray() ) {
				int sz = Array.getLength(this.rightValue);
				for ( int idx = 0; idx < sz; idx++ ) {
					list.add(Array.get(this.rightValue,idx));
				}
			} else {
				list.add(this.rightValue);
			}
		}

		return list.toArray();
	}

}
