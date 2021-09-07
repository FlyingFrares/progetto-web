package model.mo;

import java.math.*;


public class Dettaglio {
	
	
	private Long detailID;
	private Ordine order; /* N:1 */
	private Prodotto product; /* 1:1 */
	
	private Long quantità;
	private BigDecimal subtotale;
	
	
	
	public Long getDetailID() {
		return this.detailID;
	}
	
	public void setDetailID(Long detailID) {
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
	
	public void setProduct(Prodotto product) {
		this.product = product;
	}
	
	public Long getQuantità() {
		return this.quantità;
	}
	
	public void setQuantità(Long quantità) {
		this.quantità = quantità;
	}
	
	public BigDecimal getSubtotale() {
		return this.subtotale;
	}
	
	public void setSubtotale(BigDecimal subtotale) {
		this.subtotale = subtotale;
	}
	
}