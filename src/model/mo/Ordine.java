package model.mo;

import java.sql.*;
import java.math.*;

public class Ordine  {
	
	
	private Long orderID;
	private Utente user; /* N:1 */
	private String indirizzo;
	private Timestamp data;
	private String stato;
	private BigDecimal totale;
	private String IDpagamento;
	private boolean deleted;
	
	/* 1:N */
	private Dettaglio[] details;
	
	
	
	public Long getOrderID() {
		return this.orderID;
	}
	
	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}
	
	public Utente getUser() {
		return this.user;
	}
	
	public void setUser(Utente user){
		this.user = user;
	}
	
	public String getIndirizzo() {
		return this.indirizzo;
	}
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public Timestamp getData() {
		return this.data;
	}
	
	public void setData(Timestamp data) {
		this.data = data;
	}
	
	public String getStato() {
		return this.stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public BigDecimal getTotale() {
		return this.totale;
	}
	
	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}
	
	public String getIDpagamento() {
		return this.IDpagamento;
	}
	
	public void setIDpagamento(String IDpagamento) {
		this.IDpagamento = IDpagamento;
	}
	
	public boolean getDeleted() {
		return this.deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public Dettaglio[] getDetails() {
		return details;
	}
	
	public void setDetails(Dettaglio[] details) {
		this.details = details;
	}
	
	public Dettaglio getDetails(int index) {
		return this.details[index];
	}
	
	public void setDetails(int index, Dettaglio details) {
		this.details[index] = details;
	}
	
}