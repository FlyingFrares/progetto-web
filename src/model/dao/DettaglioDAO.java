package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Dettaglio;
import model.mo.Ordine;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.List;


public interface DettaglioDAO {
	
	public Dettaglio create (
			Ordine ordine,
			Prodotto prodotto,
			int quantità) throws DuplicatedObjectException;
	
	public void update(Dettaglio dettaglio) throws DuplicatedObjectException;
	
	public void delete(Dettaglio dettaglio);
	
	public Dettaglio findByDetailID(int detailID);
	
	public List<Dettaglio> findByOrderID(int orderID);
	
	public List<Dettaglio> findByProductID(int productID);
	
	/*
	setSubtotale
	*/
}
