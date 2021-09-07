package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.List;

public interface ProdottoDAO {
	
	public Prodotto create (
			Long productID,
			String nome,
			String marchio,
			String categoria,
			String descrizione,
			Long magazzino,
			BigDecimal prezzoKg,
			BigDecimal peso,
			BigDecimal prezzoTot) throws DuplicatedObjectException;
	
	public void update(Prodotto prodotto) throws DuplicatedObjectException;
	
	public void delete(Prodotto prodotto);
	
	public Long checkAvailability(Long productID);
	
	public Prodotto findByProductId(Long productID);
	
	public List<Prodotto> findByString(String searchString);
	
	public List<Prodotto> findAll();
	
}
