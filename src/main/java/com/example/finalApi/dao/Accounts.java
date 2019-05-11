package com.example.finalApi.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "accounts")
public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "idUser")
	private Integer idUser; 
	
	@Column(name = "initialBalance")
	private Float initialBalance;
	
	@Column(name="finalBalance")
	private Float finalBalance;
	
	@Column(name="nombre")
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	

	public Float getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(Float initialBalance) {
		this.initialBalance = initialBalance;
	}

	public Float getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(Float finalBalance) {
		this.finalBalance = finalBalance;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
