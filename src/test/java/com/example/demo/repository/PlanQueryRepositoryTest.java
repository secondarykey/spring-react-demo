package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.util.DateUtil;

@SpringBootTest
public class PlanQueryRepositoryTest {
	@Autowired
	PlanQueryRepository repo;

	@Test
	void testFindByPlace() {
		List<Plan> japanList = repo.findByPlace(1);
		assertNotNull(japanList);
		assertEquals(japanList.size(),2);

		List<Plan> chinaList = repo.findByPlace(2);
		assertNotNull(chinaList);
		assertEquals(chinaList.size(),1);
		
		//Plan plan = chinaList.get(0);
		//Set<PlanDetail> derails = plan.getDetails();
		//assertEquals(derails.size(),4);
	}

	@Test
	void testFindByPlaceDate() {
		Date target = DateUtil.parseDate("2021-10-10");
		Plan plan = repo.findByPlaceDate(1,target);
		assertNotNull(plan);
		Place place = plan.getPlace();
		assertNull(place);
	}

	@Test
	void testJoin() {
		List<Plan> plans = repo.joinDetail();

		assertEquals(plans.size(),3);

		for ( Plan plan : plans ) {
			List<PlanDetail> details = plan.getDetails();
			if ( plan.getId() == 1 ) {
				assertEquals(details.size(),3);
			} else if ( plan.getId() == 2 ) {
				assertEquals(details.size(),2);
			} else {
				assertEquals(details.size(),4);
			}
		}
	}
	
}
