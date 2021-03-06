package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.Exp;
import com.example.demo.mapping.core.Expression;
import com.example.demo.mapping.core.Join;
import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;

import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Operation;
import com.example.demo.model.OperationLanguage;
import com.example.demo.repository.core.QueryRepository;
import com.example.demo.util.DateUtil;

@Repository
public class OperationQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OperationQueryRepository.class);

	@Autowired(required=true)
	public OperationQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Operation> find(int orgID,Date day,String lang) {

		/*
		String sql = """
		SELECT %s FROM 
		  (SELECT * FROM OPERATION WHERE ORGANIZATION_ID = ? AND "START" <= ? AND "END" >= ?) OPE
		  LEFT OUTER JOIN (SELECT * FROM OPERATION_LANGUAGE WHERE LANGUAGE = ?) LANG
		  ON OPE.ID = LANG.OPERATION_ID
		""";
		*/

		QuerySet opeQs = QuerySet.create(Operation.class,"OPE", "ope");
		QuerySet langQs = QuerySet.create(OperationLanguage.class,"LANG", "lang");

		java.sql.Date date = DateUtil.toSQLDate(day);
		//builder.setSQL(sql, orgID,dateBuf,dateBuf,lang);
		Expression where = Exp.eq(Operation.ORGANIZATION_ID, orgID);
		where = where.and(Exp.le(Operation.START,date));
		where = where.and(Exp.ge(Operation.END,date));
		opeQs.setWhere(where);
	
		//言語のWhere句
		langQs.setWhere(Exp.eq(OperationLanguage.LANGUAGE,lang));

		//ジョインを設定
		opeQs.addJoin(Join.LeftOuter,
				langQs,
				Exp.eqName(
					opeQs.col(Operation.ID),
					langQs.col(OperationLanguage.OPERATION_ID))
				);
		SQLBuilder builder = SQLBuilder.create(opeQs);

		List<Row> rows = builder.getRows(this);
		List<Operation> opelist = new ArrayList<>();
		for ( Row row : rows ) {
			//TODO langQsの残し方
			opelist.add(row.get(opeQs));
		}
		return opelist;
	}

}
