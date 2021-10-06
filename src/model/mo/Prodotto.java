package model.mo;

import java.sql.*;
import java.math.*;

public class Prodotto {
	
	
	private int productID;
	private String nomeProdotto;
	private String marchio;
	private String categoria;
	private String descrizione;
	private int magazzino;
	private BigDecimal prezzoKg;
	private BigDecimal peso;
	private BigDecimal prezzoTot;
	
	
	
	
	public int getProductID() { return productID; }
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public String getNomeProdotto() {
		return nomeProdotto;
	}
	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	
	public String getMarchio() {
		return marchio;
	}
	public void setMarchio(String marchio) {
		this.marchio = marchio;
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getMagazzino() {
		return magazzino;
	}
	public void setMagazzino(int magazzino) {
		this.magazzino = magazzino;
	}
	
	public BigDecimal getPrezzoKg() { return prezzoKg; }
	public void setPrezzoKg(BigDecimal prezzoKg) { this.prezzoKg = prezzoKg; }
	
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	
	public BigDecimal getPrezzoTot() {
		return prezzoTot;
	}
	public void setPrezzoTot(BigDecimal prezzoTot) {
		this.prezzoTot = prezzoTot;
	}
	
}