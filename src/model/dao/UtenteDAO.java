package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Utente;

import java.util.List;

public interface UtenteDAO {
	
	public Utente create (
			int userID,
			String username,
			String admin,
			String password,
			String nome,
			String cognome,
			String email) throws DuplicatedObjectException;
	
	public void update(Utente utente) throws DuplicatedObjectException;
	
	public void delete(Utente utente);
	
	public Utente findLoggedUser();
	
	public Utente findByUsername(String username);
	
	public Utente findByUserID(int userID);
	
	public Utente findByEmail(String email);
	
	public List<Utente> findByString (String searchString);
	
	public List<Utente> findAll();
	
}
