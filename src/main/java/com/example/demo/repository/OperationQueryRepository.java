package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.OperationMapper;
import com.example.demo.mapping.QuerySet;
import com.example.demo.mapping.SQLBuilder;
import com.example.demo.model.Operation;
import com.example.demo.model.OperationLanguage;

@Repository
public class OperationQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OperationQueryRepository.class);

	@Autowired(required=true)
	public OperationQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Operation> find(int orgID,Date day,String lang) {
		String sql = """
		SELECT %s FROM 
		  (SELECT * FROM OPERATION WHERE ORGANIZATION_ID = ? AND "START" <= ? AND "END" >= ?) OPE
		  LEFT OUTER JOIN (SELECT * FROM OPERATION_LANGUAGE WHERE LANGUAGE = ?) LANG
		  ON OPE.ID = LANG.OPERATION_ID
		""";

		SQLBuilder builder = SQLBuilder.create(
			QuerySet.create(Operation.class,"OPE", "ope"),
			QuerySet.create(OperationLanguage.class,"LANG", "lang")
		);

		builder.setSQL(sql, orgID,day,day,lang);

		OperationMapper mapper = new OperationMapper(builder);
		this.query(mapper);
		return mapper.get();
	}

}
