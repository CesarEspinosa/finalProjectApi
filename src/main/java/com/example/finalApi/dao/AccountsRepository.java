package com.example.finalApi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Integer>{
	
	List<Accounts> findByIdUser(int idUser);
}
