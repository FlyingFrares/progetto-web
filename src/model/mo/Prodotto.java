package model.mo;

import java.math.*;

public class Prodotto {
	
	
	private Long productID;
	private String nomeProdotto;
	private String marchio;
	private String categoria;
	private String descrizione;
	private Long magazzino;
	private BigDecimal prezzoKg;
	private BigDecimal peso;
	private BigDecimal prezzoTot;
	
	
	
	
	public Long getProductID() {
		return this.productID;
	}
	public void setProductID(Long productID) {
		this.productID = productID;
	}
	
	public String getNomeProdotto() {
		return this.nomeProdotto;
	}
	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	
	public String getMarchio() {
		return this.marchio;
	}
	public void setMarchio(String marchio) {
		this.marchio = marchio;
	}
	
	public String getCategoria() {
		return this.categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Long getMagazzino() {
		return this.magazzino;
	}
	public void setMagazzino(Long magazzino) {
		this.magazzino = magazzino;
	}
	
	public BigDecimal getPrezzoKg() {
		return this.prezzoKg;
	}
	public void setPrezzoKg(BigDecimal prezzoKg) {
		this.prezzoKg = prezzoKg;
	}
	
	public BigDecimal getPeso() {
		return this.peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}
	
	public BigDecimal getPrezzoTot() {
		return this.prezzoTot;
	}
	public void setPrezzoTot(BigDecimal prezzoTot) {
		this.prezzoTot = prezzoTot;
	}
	
}