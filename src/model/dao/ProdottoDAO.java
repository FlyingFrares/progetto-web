package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdottoDAO {
	
	public Prodotto create (
			String nome,
			String marchio,
			String categoria,
			String descrizione,
			int magazzino,
			BigDecimal prezzoKg,
			BigDecimal peso) throws DuplicatedObjectException;
	
	public void update(Prodotto prodotto) throws DuplicatedObjectException;
	
	public void delete(Prodotto prodotto);
	
	public int checkAvailability(int productID);
	
	public Prodotto findByProductId(int productID);
	
	public List<Prodotto> findByCategory(String category);
	
	public List<Prodotto> findByString(String searchString);
	
	public List<Prodotto> findAll();
	
}
