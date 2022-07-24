package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.model.Organization;
import com.example.demo.util.DateUtil;

@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("test")
public class OrganizationRepositoryTest {
	
	@Autowired
	OrganizationQueryRepository repo;
	
	@Test
	void testFindBelong() {
		Date date = DateUtil.parseClient("2022-01-01 00:00");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		List<Organization> orgs = repo.find(cal.getTime());
		assertEquals(orgs.size(),1,"適用日以前の組織が取れないこと");
		
		cal.add(Calendar.MONTH, 1);
		orgs = repo.find(cal.getTime());
		assertEquals(orgs.size(),8,"適用日後の組織が取れないこと");
	}

}
