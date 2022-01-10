package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.mapping.ModelMapper;

/**
 * クエリ発行用のリポジトリ
 * <pre>
 * ModelMapperを利用してオブジェクトをマッピングする仕組みを持つRepository
 * Mapperに設定してある、SQL,QuerySet,Pagingを利用して
 * SQLを発行します。JDBCTemplateを利用してアクセスします。
 * </pre>
 */
public class QueryRepository {
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(QueryRepository.class);

	//NamedParameterJdbcTemplate;
	protected final JdbcTemplate template;
	public QueryRepository(JdbcTemplate template) {
		this.template = template;
	}
	
	protected <T> T query(ModelMapper<T> mapper) {
		if ( mapper.isPaging() ) {
			this.setCount(mapper);
		}

		try {
			template.query(mapper.getSQL(),mapper,mapper.getArguments());
		} catch (EmptyResultDataAccessException ex) {
			//0件時の処理
			logger.warn("戻り値がありませんでした。",ex);
		}
		return mapper.get();
	}

	protected <T> void setCount(ModelMapper<T> mapper) {
		String sql = mapper.getCountSQL();
		int cnt = 0;
		try {
			cnt = template.queryForObject(sql, Integer.class ,mapper.getCountArguments());
		} catch (EmptyResultDataAccessException ex) {
			//0件時の処理
			logger.warn("戻り値がありませんでした。",ex);
			mapper.setCount(0);
		}
		mapper.setCount(cnt);
	}
}
