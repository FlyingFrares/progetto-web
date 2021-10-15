package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Carrello;
import model.mo.Utente;
import model.mo.Prodotto;

import java.util.List;

public interface CarrelloDAO {
	
	public Carrello create (
			int cartID,
			Utente utente,
			Prodotto prodotto,
			int quantità) throws DuplicatedObjectException;
	
	public void update(Carrello carrello) throws DuplicatedObjectException;
	
	public void delete(Carrello carrello);
	
	public List<Carrello> findByUserID(int userID);
}
