package model.mo;

import java.sql.*;
import java.math.*;

public class Dettaglio {
	
	
	private int detailID;
	private Ordine order; /* N:1 */
	private Prodotto product; /* 1:1 */
	
	private int quantità;
	private BigDecimal subtotale;
	
	
	
	public int getDetailID() {
		return this.detailID;
	}
	
	public void setDetailID(int detailID) {
		this.detailID = detailID;
	}
	
	public Ordine getOrder() {
		return this.order;
	}
	
	public void setOrder (Ordine order) {
		this.order = order;
	}
	
	public Prodotto getProduct() {
		return this.product;
	}
	
	public void setProduct(Prodotto product) { this.product = product;	}
	
	public int getQuantità() {
		return this.quantità;
	}
	
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}
	
	public BigDecimal getSubtotale() {
		return this.subtotale;
	}
	
	public void setSubtotale(BigDecimal subtotale) {
		this.subtotale = subtotale;
	}
	
}