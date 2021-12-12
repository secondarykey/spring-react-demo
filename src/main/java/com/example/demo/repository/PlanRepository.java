package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;

@Repository
public interface PlanRepository extends CrudRepository<Plan,Integer>{
}
