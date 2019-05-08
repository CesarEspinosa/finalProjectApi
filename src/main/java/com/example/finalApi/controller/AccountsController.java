package com.example.finalApi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.finalApi.dao.Accounts;
import com.example.finalApi.dao.AccountsRepository;
import com.example.finalApi.dao.Trx;
import com.example.finalApi.dao.TrxRepository;

@Controller
@RequestMapping(path = "/final")
public class AccountsController {
	@Autowired
	private AccountsRepository accountsRepository; 
	
	@Autowired 
	private TrxRepository trxRepository;
	
	@GetMapping(path = "/accounts")
	public ResponseEntity<List<Accounts>>  getAccounts(@RequestParam int userId) {
		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		List<Accounts> list = accountsRepository.findByIdUser(userId);
		ResponseEntity<List<Accounts>> res = new ResponseEntity<>(list, headers, HttpStatus.OK);
		return res;
	}
	
	@GetMapping(path = "/accounts/trx")
	public ResponseEntity<List<Trx>> getTrx(@RequestParam int idAccount){
		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		List<Trx> list = trxRepository.findByIdAccount(idAccount);
		ResponseEntity<List<Trx>> res = new ResponseEntity<>(list, headers, HttpStatus.OK);
		return res;
	}
	
	@GetMapping(path = "/accounts/account")
	public ResponseEntity<Accounts> getAccount(@RequestParam int id){
		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		Accounts account = accountsRepository.findById(id).orElse(new Accounts());
		return new ResponseEntity<>(account, headers, HttpStatus.OK);
	}
}
