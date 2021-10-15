package model.mo;

import java.sql.*;
import java.math.*;
import java.util.PrimitiveIterator;

public class Carrello {
	
	
	private int cartID;
	private Utente user; /* N:1 */
	private Prodotto product; /* 1:1 */
	private int quantità;
	private boolean deleted;
	
	public int getCartID() { return this.cartID; }
	public void setCartID(int cartID) { this.cartID = cartID; }
	
	public Utente getUser() { return this.user; }
	public void setUser(Utente user) { this.user = user; }
	
	public Prodotto getProduct() { return this.product; }
	public void setProduct(Prodotto product) { this.product = product; }
	
	public int getQuantità() { return this.quantità; }
	public void setQuantità(int quantità) { this.quantità = quantità; }
	
	public boolean getDeleted() { return this.deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
}
