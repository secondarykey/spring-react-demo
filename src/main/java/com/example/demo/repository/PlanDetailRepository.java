package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlanDetail;

@Repository
public interface PlanDetailRepository extends CrudRepository<PlanDetail,Integer> {

	@Query(""" 
		SELECT DISTINCT * FROM PLAN_DETAILS 
		  INNER JOIN (
		    SELECT * FROM PLANS WHERE PLANS.PLACES_ID = :placeId AND PLANS.DATE = :date
		  ) PLANS ON PLANS.id = PLAN_DETAILS.plans_id
		""")
	List<PlanDetail> findByPlaceDate(@Param("placeId") Integer placeId,@Param("date") Date date);
}
