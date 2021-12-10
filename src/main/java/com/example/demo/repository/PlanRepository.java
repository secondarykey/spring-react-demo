package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;

@Repository
public interface PlanRepository extends CrudRepository<Plan,Integer>{

	@Query("SELECT * FROM PLANS WHERE PLACES_ID = :placeId")
	public List<Plan> findByPlace(@Param("placeId") Integer placeId);

	@Query("SELECT * FROM PLANS WHERE PLACES_ID = :placeId AND DATE = :date")
	public Plan findByPlaceDate(@Param("placeId") Integer placeId,@Param("date") Date date);
}
