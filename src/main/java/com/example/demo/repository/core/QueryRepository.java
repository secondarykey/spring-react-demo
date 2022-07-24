package com.example.demo.repository.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.example.demo.mapping.core.ModelMapper;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;

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

	/**
	 * 操作用テンプレート
	 */
	protected final JdbcTemplate template;
	
	/**
	 * コンストラクタ
	 * @param template 操作テンプレート
	 */
	public QueryRepository(JdbcTemplate template) {
		this.template = template;
	}

	/**
	 * １件のデータ
	 * <pre>
	 * SQLを実行し、SQLの結果を取得。１件目のデータを取り出す
	 * 基本的に１件しかないＳＱＬの時に呼び出す（複数件存在する場合は警告ログを出す）
	 * </pre>
	 * @param builder SQLビルダー
	 * @return 行データ
	 */
	protected Row singleQuery(SQLBuilder builder) {
		ModelMapper mapper = run(builder);
		return mapper.singleResult();
	}

	/**
	 * クエリ発行
	 * <pre>
	 * SQLを実行し、SQLの結果を取得
	 * </pre>
	 * @param builder SQLビルダー
	 * @return 行リスト
	 */
	protected List<Row> query(SQLBuilder builder) {
		ModelMapper mapper = run(builder);
		return mapper.result();
	}
	

	/**
	 * カウント設定
	 * <pre>
	 * 指定しているSQLにcount(*)を埋め込む
	 * </pre>
	 * @param builder SQLビルダー
	 * @return カウント数
	 */
	protected int count(SQLBuilder builder) {
		String sql = builder.getCountSQL();
		int cnt = 0;
		try {
			cnt = template.queryForObject(sql, Integer.class ,builder.getCountArgs());
		} catch (EmptyResultDataAccessException ex) {
			//0件時の処理
			logger.warn("戻り値がありませんでした。",ex);
		}
		return cnt;
	}

	/**
	 * SQL実行
	 * <pre>
	 * SQLBuilderにPagingがある場合カウントを呼び出して件数を設定する
	 * </pre>
	 * @param builder 対象ビルダー
	 * @return マッパー
	 */
	private ModelMapper run(SQLBuilder builder) {

		ModelMapper mapper = new ModelMapper(builder);
		if ( builder.isPaging() ) {
			this.setCount(builder);
		}

		try {
			template.query(builder.getSQL(),mapper,builder.getCountArgs());
		} catch (EmptyResultDataAccessException ex) {
			//0件時の処理
			logger.warn("戻り値がありませんでした。",ex);
		}
		return mapper;
	}

	/**
	 * カウントの設定
	 * <pre>
	 * 指定されているmapperからカウント文を発行
	 * ModelMapperにデータ件数として登録しておく
	 * </pre>
	 * @param mapper マッパーオブジェクト
	 */
	protected void setCount(SQLBuilder builder) {
		String sql = builder.getCountSQL();
		int cnt = 0;
		try {
			cnt = template.queryForObject(sql, Integer.class ,builder.getCountArgs());
		} catch (EmptyResultDataAccessException ex) {
			//0件時の処理
			logger.warn("戻り値がありませんでした。",ex);
			builder.setCount(0);
		}
		builder.setCount(cnt);
	}
	

	/**
	 * CSV(?,?,?)の作成
	 * <pre>
	 * IN句用にCollectionデータ数?のCSVを作成
	 * </pre>
	 * @param ids 対象引数
	 * @return 引数数の？のCSV
	 */
	@SuppressWarnings("unused")
	protected String createQuestionCSV(Collection<?> ids) {
		StringBuffer buffer = new StringBuffer();
		for ( Object obj : ids ) {
			if ( buffer.length() != 0 ) {;
				buffer.append(",");
			}
			buffer.append("?");
		}
		return buffer.toString();
	}

	/**
	 * Collection引数展開
	 * <pre>
	 * Collectionデータをオブジェクトとして分解してその他引数と併せて配列化する
	 * ※SQLBuilder.setSQL() の引数にIN句用の引数を作成する
	 * </pre>
	 * @param args 引数
	 * @return 引数オブジェクトの配列
	 */
	protected Object[] createArguments(Object... args) {
		List<Object> rtn = new ArrayList<>();
		for ( Object obj : args ) {
			if ( obj instanceof Collection ) {
				Collection<?> list = (Collection<?>)obj;
				for ( Object wk : list ) {
					rtn.add(wk);
				}
			} else {
				rtn.add(obj);
			}
		}
		Object[] arrays = new Object[rtn.size()];
		rtn.toArray(arrays);
		return arrays;
	}

	public void query(String sql, Object[] vals,RowCallbackHandler handler) {
		template.query(sql,handler,vals);
	}
}
