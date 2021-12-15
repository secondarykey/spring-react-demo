package com.example.demo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlanDetail;

@Repository
public interface PlanDetailRepository extends CrudRepository<PlanDetail,Integer> {
}
