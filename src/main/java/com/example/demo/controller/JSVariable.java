package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * オブジェクトのJS設定
 * <pre>
 * オブジェクトをJavaScript形式で書き出す
 * 
 * 単体はオブジェクトとして定義されます。
 * add() でオブジェクト内に変数を定義、
 * createObject()はオブジェクト内オブジェクトを生成します。
 * createObject()で作成したものは再帰で設定されます。
 * </pre>
 */
public class JSVariable {

	/**
	 * 型の種類
	 * 
	 */
	public enum JSType {
		String, Number;
	}

	/**
	 * 一時オブジェクトの名称
	 */
	private final static String workName = "tmpJSWriter_%s";

	/**
	 * 親変数
	 */
	private JSVariable parent;
	/**
	 * 変数名称
	 */
	private String name;
	/**
	 * 一時変数名称
	 */
	private String tmpName;

	/**
	 * 変数行
	 */
	private List<String> lines = new ArrayList<>();
	/**
	 * 変数内オブジェクト
	 */
	private List<JSVariable> children = new ArrayList<>();

	/**
	 * コンストラクタ
	 * @param name 変数名称
	 */
	public JSVariable(String name) {
		this.name = name;
		this.tmpName = String.format(workName, name);
	}

	/**
	 * JavaScript出力
	 * <pre>
	 * 設定されているオブジェクトでJavaScriptを出力
	 * 
	 * </pre>
	 * @param body
	 */
	public void write(StringBuffer body) {

		// 仮変数を定義
		defineObject(body, tmpName);
		// 定義を設定
		for (String line : lines) {
			addLine(body, line);
		}
		// 子オブジェクト数回繰り返す
		for (JSVariable child : children) {
			child.write(body);
		}

		String right = "var " + name;
		if (parent != null) {
			right = String.format("%s.%s", parent.tmpName, name);
		}
		// 変数名で代入
		addLine(body, String.format("%s = %s", right, tmpName));
	}

	/**
	 * オブジェクトの追加
	 * 
	 * @param name 子の名称を設定
	 * @return
	 */
	public JSVariable createObject(String name) {
		JSVariable var = new JSVariable(name);
		children.add(var);
		var.parent = this;
		return var;
	}

	/**
	 * 変数の設定
	 * @param key
	 * @param t
	 * @param obj
	 */
	public void add(String key, JSType t, String obj) {
		addObject(key,t,obj);
	}

	/**
	 * 配列の設定
	 * @param key
	 * @param t
	 * @param obj
	 */
	public void add(String key, JSType t, String[] obj) {
		addObject(key,t,obj);
	}

	/**
	 * 変数設定
	 * @param key 格納名称
	 * @param t   格納タイプ
	 * @param obj 格納値
	 */
	private void addObject(String key, JSType t, Object obj) {
		// 左辺の設定
		String left = String.format("%s.%s", tmpName, key);
		String right = "";
		if (obj instanceof String) {
			right = String.format("%s", getValue(t, obj));
		} else if (obj instanceof String[]) {
			// 右辺の設定
			right = String.format("[%s]", getValue(t, obj));
		} else {
			throw new RuntimeException("対応してない型です");
		}
		// 行に追加
		lines.add(String.format("%s = %s", left, right));
	}

	/**
	 * 定義書き出し
	 * 
	 * @param name
	 */
	private static void defineObject(StringBuffer buf, String name) {
		addLine(buf, String.format("var %s = {}", name));
	}

	/**
	 * １行追加
	 * 
	 * @param line
	 */
	private static void addLine(StringBuffer buf, String line) {
		buf.append(String.format("%s;\n", line));
	}

	/**
	 * 値の取得
	 * 
	 * @param t   JSの型
	 * @param obj 対象オブジェクト
	 * @return その型に応じた変数の形
	 */
	private static String getValue(JSVariable.JSType t, Object obj) {
		String val = "";
		if (obj instanceof String) {
			if (t == JSType.String) {
				val = formatString((String) obj);
			} else {
				val = formatNumber((String) obj);
			}
		} else if (obj instanceof String[]) {
			String[] arr = (String[]) obj;
			for (String tmp : arr) {
				String work = getValue(t, tmp);
				if (val.length() != 0) {
					val += ",";
				}
				val += work;
			}
		} else {
			throw new RuntimeException("対応してない型です");
		}
		return val;
	}

	/**
	 * 文字列の描画
	 * 
	 * @param val
	 * @return
	 */
	private static String formatString(String val) {
		return String.format("'%s'", val);
	}

	/**
	 * 数値型の描画
	 * @param val
	 * @return
	 */
	private static String formatNumber(String val) {
		return String.format("%s", val);
	}
}