package com.example.demo.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.User;
public interface UserJoinRepository extends CrudRepository<User, String> {
    @Query("SELECT DISTINCT U.*,roleObj.* FROM USERS U JOIN Role roleObj ON U.ROLE = roleObj.ID AND U.ID = :id")
    User findByOther(@Param("id") String id);
    @Query("SELECT U.*,roleObj.* FROM USERS U JOIN Role roleObj ON U.ROLE = roleObj.ID AND U.NAME = :name")
    User findByName(@Param("name") String name);
}
