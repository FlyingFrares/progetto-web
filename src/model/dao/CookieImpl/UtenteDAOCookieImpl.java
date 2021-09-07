package model.dao.CookieImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UtenteDAO;
import model.mo.Utente;

import java.util.List;


public class UtenteDAOCookieImpl implements UtenteDAO {
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@Override
	public Utente create(
			Long userID,
			String username,
			String admin,
			String password,
			String nome,
			String cognome,
			String email) {
		
		Utente loggedUser = new Utente();
		loggedUser.setUserID(userID);
		loggedUser.setNome(nome);
		loggedUser.setCognome(cognome);
		
		Cookie cookie;
		cookie = new Cookie("loggedUser", encode(loggedUser));
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return loggedUser;
		
	}
	
	@Override
	public void update(Utente loggedUser) {
		
		Cookie cookie;
		cookie = new Cookie("loggedUser", encode(loggedUser));
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}
	
	@Override
	public void delete(Utente loggedUser) {
		
		Cookie cookie;
		cookie = new Cookie("loggedUser", "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}
	
	@Override
	public Utente findLoggedUser() {
		
		Cookie[] cookies = request.getCookies();
		Utente loggedUser = null;
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length && loggedUser == null; i++) {
				if (cookies[i].getName().equals("loggedUser")) {
					loggedUser = decode(cookies[i].getValue());
				}
			}
		}
		
		return loggedUser;
		
	}
	
	@Override
	public Utente findByUsername(String username) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public Utente findByEmail(String email) { throw new UnsupportedOperationException("Not supported yet."); }
	
	@Override
	public List<Utente> findAll() { throw new UnsupportedOperationException("Not supported yet."); }
	
	private String encode(Utente loggedUser) {
		
		String encodedLoggedUser;
		encodedLoggedUser = loggedUser.getUserID() + "#" + loggedUser.getNome() + "#" + loggedUser.getCognome();
		return encodedLoggedUser;
		
	}
	
	private Utente decode(String encodedLoggedUser) {
		
		Utente loggedUser = new Utente();
		
		String[] values = encodedLoggedUser.split("#");
		
		loggedUser.setUserID(Long.parseLong(values[0]));
		loggedUser.setNome(values[1]);
		loggedUser.setCognome(values[2]);
		
		return loggedUser;
		
	}
	
}
