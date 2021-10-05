package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Dettaglio;
import model.mo.Ordine;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.List;


public interface DettaglioDAO {
	
	public Dettaglio create (
			int detailID,
			Ordine ordine,
			Prodotto prodotto,
			int quantità,
			BigDecimal subtotale) throws DuplicatedObjectException;
	
	public void update(Dettaglio dettaglio) throws DuplicatedObjectException;
	
	public void delete(Dettaglio dettaglio);
	
	public Dettaglio findByDetailID(Long detailID);
	
	public List<Dettaglio> findByOrderID(Long orderID);
	
	public List<Dettaglio> findByProductID(Long productID);
	
	/*
	setSubtotale
	*/
}
