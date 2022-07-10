package com.example.demo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PlanDetail;
import com.example.demo.model.PlanDetailPK;

@Repository
public interface PlanDetailRepository extends CrudRepository<PlanDetail,PlanDetailPK> {
}
