package model.mo;

import java.sql.*;
import java.math.*;

public class Utente {
	
	private int userID;
	private String username;
	private String admin;
	private String password;
	private String nome;
	private String cognome;
	private String email;
	private boolean deleted;
	
	/* 1:N */
	private Ordine[] orders;
	
	private Carrello[] carrelli;
	
	public int getUserID() { return this.userID; }
	
	public void setUserID(int userID) { this.userID = userID; }
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAdmin() {
		return this.admin;
	}
	
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public Ordine[] getOrders() {
		return orders;
	}
	
	public void setOrders(Ordine[] orders) {
		this.orders = orders;
	}
	
	public Ordine getOrders(int index) {
		return this.orders[index];
	}
	
	public void setOrders(int index, Ordine orders) {
		this.orders[index] = orders;
	}
	
	public Carrello[] getCarrelli() { return carrelli; }
	
	public void setCarrelli(Carrello[] carrelli) { this.carrelli = carrelli; }
	
	public Carrello getCarrelli(int index) { return this.carrelli[index]; }
	
	public void setCarrelli(int index, Carrello carrelli) {	this.carrelli[index] = carrelli; }
	
}
