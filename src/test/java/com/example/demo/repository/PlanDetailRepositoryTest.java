package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.model.Place;
import com.example.demo.model.Plan;
import com.example.demo.model.PlanDetail;
import com.example.demo.model.PlanDetailPK;
import com.example.demo.util.DateUtil;

@ActiveProfiles("test")
@SpringBootTest(classes = DemoApplication.class)
public class PlanDetailRepositoryTest {

	@Autowired
	PlanDetailRepository repo;

	@Test
	void testFind() {
		PlanDetailPK pk = new PlanDetailPK();
		Optional<PlanDetail> japan = repo.findById(pk);
		
		
		
		
		
	}

}
