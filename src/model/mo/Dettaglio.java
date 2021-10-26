package model.mo;

public class Dettaglio {
	
	
	private int detailID;
	private Ordine order; /* N:1 */
	private Prodotto product; /* 1:1 */
	
	private int quantità;
	
	
	
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
	
}