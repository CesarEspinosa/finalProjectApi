package com.example.finalApi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
	List<User> findByUserAndPassword(String user, String password);
	List<User> findByUser(String user);
}
