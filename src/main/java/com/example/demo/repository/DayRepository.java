package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Day;

@Repository
public interface DayRepository extends CrudRepository<Day,String> {
}
