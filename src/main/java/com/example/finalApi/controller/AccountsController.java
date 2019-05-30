package com.example.finalApi.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.example.finalApi.dao.Categories;
import com.example.finalApi.dao.CategoriesRepository;
import com.example.finalApi.dao.Trx;
import com.example.finalApi.dao.TrxRepository;
import com.example.finalApi.dao.User;
import com.example.finalApi.dao.UserRepository;

@Controller
@RequestMapping(path = "/final")
public class AccountsController {
	@Autowired
	private AccountsRepository accountsRepository; 
	
	@Autowired 
	private TrxRepository trxRepository;
	
	@Autowired
	private CategoriesRepository categoriesRepository;
	
	@Autowired
	private UserRepository userRepository; 
	
	@GetMapping(path = "/accounts")
	public ResponseEntity<List<Accounts>>  getAccounts(@RequestParam int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		List<Accounts> list = accountsRepository.findByIdUser(userId);
		ResponseEntity<List<Accounts>> res = new ResponseEntity<>(list, headers, HttpStatus.OK);
		return res;
	}
	
	@GetMapping(path = "/accounts/trx")
	public ResponseEntity<List<Trx>> getTrx(@RequestParam int idAccount){
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		List<Trx> list = trxRepository.findByIdAccount(idAccount, sortByIdAsc());
		ResponseEntity<List<Trx>> res = new ResponseEntity<>(list, headers, HttpStatus.OK);
		return res;
	}
	
	private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.DESC, "id");
    }
	
	@GetMapping(path = "/accounts/account")
	public ResponseEntity<Accounts> getAccount(@RequestParam int id){
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		Accounts account = accountsRepository.findById(id).orElse(new Accounts());
		return new ResponseEntity<>(account, headers, HttpStatus.OK);
	}
	
	@GetMapping (path = "/accounts/delete")
	public ResponseEntity<String> deleteAccount(@RequestParam int id){
		accountsRepository.deleteById(id);
		return new ResponseEntity<>("", getHeaders(), HttpStatus.OK);
	}
	
	@GetMapping (path = "/categories")
	public ResponseEntity<Iterable<Categories>> getCategories(){
		Iterable<Categories> categories = categoriesRepository.findAll();
		return new ResponseEntity<>(categories, getHeaders(), HttpStatus.OK);
	}
	
	@GetMapping (path = "/newTrx")
	public ResponseEntity<String> newTrx(
			@RequestParam int idAccount, 
			@RequestParam int type, 
			@RequestParam int category, 
			@RequestParam float qty, 
			@RequestParam String name, 
			@RequestParam String date){
		Accounts account = accountsRepository.findById(idAccount).orElse(new Accounts());
		Trx trx = new Trx(); 
		trx.setIdAccount(idAccount);
		trx.setCategory(category);
		trx.setType(type);
		trx.setQty(qty);
		trx.setName(name);
		String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		trx.setDate(today);
		
		trxRepository.save(trx); 
		float oldBalance = account.getFinalBalance(); 
		float balance = oldBalance;
		if(type == 1) {
			balance += qty; 
		}else {
			balance -= qty;
		}
		account.setFinalBalance(balance);
		
		accountsRepository.save(account); 
		
		return new ResponseEntity<String>("", getHeaders(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/accounts/findUser")
	public ResponseEntity<List<User>> findUser(@RequestParam String user, 
			@RequestParam String password){
		List<User> users = new ArrayList<>();
		List<User> listUsers = userRepository.findByUserAndPassword(user, password); 
		if(!listUsers.isEmpty()) {
			users = listUsers;
		}else {
			User newUser = new User(); 
			users.add(newUser);
		}
		return new ResponseEntity<List<User>>(users, getHeaders(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/accounts/newUser")
	public ResponseEntity<User> newUser(@RequestParam String user, 
			@RequestParam String password, 
			@RequestParam String name){
		List<User> findUser = userRepository.findByUser(user);
		User userSaved = new User();
		if(findUser.isEmpty()) {
			User newUser = new User();
			newUser.setName(name);
			newUser.setUser(user);
			newUser.setPassword(password);
			userSaved = userRepository.save(newUser);
		}
		return new ResponseEntity<User>(userSaved, getHeaders(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/accounts/addAccount")
	public ResponseEntity<Accounts> addAcount(@RequestParam int id, 
			@RequestParam float initialBalance, 
			@RequestParam String name, 
			@RequestParam String issuer){
		Accounts account = new Accounts(); 
		account.setIdUser(id);
		account.setInitialBalance(initialBalance);
		account.setFinalBalance(initialBalance);
		account.setNombre(name);
		account.setIssuer(issuer);
		
		account = accountsRepository.save(account);
		
		Trx trx = new Trx();
		trx.setIdAccount(account.getId());
		trx.setType(1);
		trx.setCategory(11);
		trx.setQty(initialBalance);
		trx.setName("Saldo Inicial");
		String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		trx.setDate(today);
		
		trx = trxRepository.save(trx);
		
		return new ResponseEntity<>(account, getHeaders(), HttpStatus.OK);
	}
	
	
	
	
	
	private HttpHeaders getHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Content-Type, X-Auth-Token");
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		return headers;
	}
}
