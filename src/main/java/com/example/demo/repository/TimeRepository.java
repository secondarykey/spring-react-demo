package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Times;

@Repository
public interface TimeRepository extends CrudRepository<Times,Integer> {
}
