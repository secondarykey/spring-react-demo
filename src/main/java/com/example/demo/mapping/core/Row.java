package com.example.demo.mapping.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.core.Model;

/**
 * ModelMapperにより作成されるオブジェクト群
 * @author p230985
 */
public class Row {
	
	private static final Logger logger = LoggerFactory.getLogger(Row.class);
	
	/**
	 * 格納用マップ
	 */
	private Map<String,Model> map = new HashMap<>();
	
	/**
	 * モデルデータの追加
	 * @param key 格納キー
	 * @param model モデルデータ
	 */
	public void add(String key,Model model) {
		if ( map.containsKey(key) ) {
			logger.warn("設定しているキーはすでに存在します。{}",key);
		}
		map.put(key,model);
	}
	
	/**
	 * モデルオブジェクトの取得
	 * @param key モデル格納キー
	 * @param clazz 指定クラス
	 * @return モデルデータ
	 */
	@SuppressWarnings("unchecked")
	public <T extends Model> T get(String key, Class<T> clazz) {
		Model obj = map.get(key);
		if ( obj == null ) {
			return auto(clazz);
		}
		return (T)obj;
	}

	/**
	 * 自動生成
	 * @param clazz
	 * @return
	 */
	private <T extends Model> T auto(Class<? extends Model> clazz) {
		//TODO 何か自動的に行う場合に処理
		return null;
	}

	/**
	 * モデルオブジェクト取得
	 * @param qs クエリセット
	 * @return 対象クラスオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public<T extends Model> T get(QuerySet qs) {
		Model obj = map.get(qs.getAliasPrefix());
		if ( obj == null ) {
			Class<? extends Model> modelClass = qs.getModelClass();
			return auto(modelClass);
		}
		return (T)obj;
	}
}
