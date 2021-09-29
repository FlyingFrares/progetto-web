package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Utente;

import java.util.List;

public interface UtenteDAO {
	
	public Utente create (
			Long userID,
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
	
	public Utente findByEmail(String email);
	
	public List<Utente> findAll();
	
}
