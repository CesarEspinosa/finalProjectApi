package com.example.finalApi.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrxRepository extends CrudRepository<Trx, Integer>{
	List<Trx> findByIdAccount(int idAccount);
}
