package com.example.demo.mapping.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.model.core.Model;

/**
 * ResultSetからオブジェクトを取り出すMapper
 * @author p230985
 */
public class QueryMapper implements RowCallbackHandler {

	private static final Logger logger = LoggerFactory.getLogger(QueryMapper.class);
	/**
	 * SQLBuilder
	 */
	private QuerySet[] queries;
	
	/**
	 * コンストラクタ
	 * @param builder 対象のSQLビルダー
	 */
	public QueryMapper(QuerySet ... queries) {
		this.queries = queries;
	}

	private List<Row> resultList = new ArrayList<>();
	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Row map = new Row();
		for ( QuerySet set : queries ) {
			Class<? extends Model> clazz = set.getModelClass();
			Model t = SQLBuilder.create(clazz,set, rs);
			String key = set.getAliasPrefix();
			map.add(key, t);
		}
		resultList.add(map);
	}

	/**
	 * 結果取得
	 * @return
	 */
	public List<Row> result() {
		return resultList;
	}

	/**
	 * 結果１件取得
	 * @return
	 */
	public Row singleResult() {
		int leng = resultList.size();
		if ( leng >= 1 ) {
			if ( leng > 1 ) {
				logger.warn("singleResult() で{}件のデータが存在します。",leng);
			}
			return resultList.get(0);
		}
		return null;
	}

}
