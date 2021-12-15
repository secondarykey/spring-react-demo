package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Time;

@Repository
public interface TimeRepository extends CrudRepository<Time,Integer> {
}
