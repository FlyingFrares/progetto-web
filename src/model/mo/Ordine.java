package model.mo;

import java.sql.*;
import java.math.*;

public class Ordine  {
	
	
	private int orderID;
	private Utente user; /* N:1 */
	private String destinatario;
	private String indirizzo;
	private Timestamp data;
	private BigDecimal totale;
	private String IDpagamento;
	private String intestatario;
	private String stato;
	private boolean deleted;
	
	/* 1:N */
	private Dettaglio[] details;
	
	
	
	public int getOrderID() { return this.orderID;	}
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public Utente getUser() {
		return this.user;
	}
	
	public void setUser(Utente user){
		this.user = user;
	}
	
	public String getDestinatario() { return this.destinatario; }
	
	public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
	
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
	
	public String getIntestatario() { return this.intestatario; }
	
	public void setIntestatario(String intestatario) { this.intestatario = intestatario; }
	
	public String getStato() { return this.stato; }
	
	public void setStato(String stato) { this.stato = stato; }
	
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