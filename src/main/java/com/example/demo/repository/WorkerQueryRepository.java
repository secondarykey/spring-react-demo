package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.mapping.core.QuerySet;
import com.example.demo.mapping.core.Row;
import com.example.demo.mapping.core.SQLBuilder;
import com.example.demo.model.Users;
import com.example.demo.model.Worker;
import com.example.demo.util.DateUtil;

@Repository
public class WorkerQueryRepository extends QueryRepository {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WorkerQueryRepository.class);

	@Autowired(required=true)
	public WorkerQueryRepository(JdbcTemplate template) {
		super(template);
	}

	public List<Worker> find(int orgID,Date day) {
		String sql = """
		SELECT %s FROM 
		  (SELECT WORKER.* FROM WORKER INNER JOIN (SELECT * FROM OPERATION WHERE ORGANIZATION_ID = ?) OPE 
		       ON WORKER.OPERATION_ID = OPE.ID) WORK
		    LEFT OUTER JOIN (SELECT * FROM USERS) USER
		    ON USER.ID = WORK.USER_ID
		  WHERE WORK."DATE" = ?
		""";

		QuerySet workQs = QuerySet.create(Worker.class,"WORK", "w");
		QuerySet userQs = QuerySet.create(Users.class,"USER", "u");

		SQLBuilder builder = SQLBuilder.create(workQs,userQs);

		String dateBuf = DateUtil.sqlDay(day);
		builder.setSQL(sql,orgID,dateBuf);

		List<Row> rows = this.query(builder);
		List<Worker> list = new ArrayList<>();
		for ( Row row : rows ) {
			list.add(row.get(workQs));
		}
		return list;
	}

}
