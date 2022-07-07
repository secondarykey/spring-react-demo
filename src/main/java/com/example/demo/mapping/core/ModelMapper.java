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
public class ModelMapper implements RowCallbackHandler {

	private static final Logger logger = LoggerFactory.getLogger(ModelMapper.class);
	/**
	 * SQLBuilder
	 */
	private SQLBuilder builder;
	
	/**
	 * コンストラクタ
	 * @param builder 対象のSQLビルダー
	 */
	public ModelMapper(SQLBuilder builder) {
		this.builder = builder;
	}
	
	private List<Row> resultList = new ArrayList<>();

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		List<QuerySet> sets = builder.getQuerySets();
		Row map = new Row();
		for ( QuerySet set : sets ) {
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

	public String getSQL() {
		return builder.getSQL();
	}

	public Object[] getArguments() {
		return builder.getArgs();
	}	

	/**
	 * カウントSQLの取得 
	 * @return
	 */
	public String getCountSQL() {
		return this.builder.getCountSQL();
	}

	public Object[] getCountArguments() {
		return this.builder.getCountArgs();
	}

	public void setCount(int cnt) {
		this.builder.setCount(cnt);
	}

	public boolean isPaging() {
		return this.builder.isPaging();
	}
}
