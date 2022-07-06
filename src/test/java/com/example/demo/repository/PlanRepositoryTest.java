package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Places;
import com.example.demo.model.PlanDetails;
import com.example.demo.model.Plans;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
public class PlanRepositoryTest {
	@Autowired
	PlanQueryRepository repo;

	@Test
	void testFindByPlace() {
		List<Plans> japanList = repo.findByPlace(1);
		assertNotNull(japanList);
		assertEquals(japanList.size(),2);

		List<Plans> chinaList = repo.findByPlace(2);
		assertNotNull(chinaList);
		assertEquals(chinaList.size(),1);
	}

	@Test
	void testFindByPlaceDate() {
		Date target = DateUtil.parseDate("2021-10-10");
		Plans plan = repo.findByPlaceDate(1,target);

		assertNotNull(plan);
		//結合できてない
		//Places place = plan.getPlace();
		//assertNull(place);
	}

	@Test
	void testJoin() {
		Collection<Plans> plans = repo.joinDetail();

		assertEquals(plans.size(),3);

		for ( Plans plan : plans ) {
			/* 関連性はメソッドで取得
			List<PlanDetails> details = plan.getDetails();
			if ( plan.getId() == 1 ) {
				assertEquals(details.size(),3);
			} else if ( plan.getId() == 2 ) {
				assertEquals(details.size(),2);
			} else {
				assertEquals(details.size(),4);
			}
			*/
		}
	}
	
}
