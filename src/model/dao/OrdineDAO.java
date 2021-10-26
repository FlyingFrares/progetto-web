package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Ordine;
import model.mo.Utente;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


public interface OrdineDAO {
	
	public Ordine create (
			Utente utente,
			String destinatario,
			String indirizzo,
			BigDecimal totale,
			String IDpagamento,
			String intestatario) throws DuplicatedObjectException;
	
	public void update(Ordine ordine) throws DuplicatedObjectException;
	
	public void delete(Ordine ordine);
	
	public Ordine findByOrderID(int orderID);
	
	public List<Ordine> findByUserID(int userID);
	
	public List<Ordine> findAll();
}
