package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import model.dao.ProdottoDAO;
import model.mo.Prodotto;
import services.config.Configuration;
import services.log.LogService;

import model.mo.Utente;
import model.dao.DAOFactory;
import model.dao.UtenteDAO;

public class Admin {
	
	private Admin() {
	}
	
	public static void view(HttpServletRequest request, HttpServletResponse response) {
		
		DAOFactory sessionDAOFactory = null;
		DAOFactory daoFactory = null;
		Utente loggedUser;
		
		Logger logger = LogService.getApplicationLogger();
		
		try {
			
			Map sessionFactoryParameters = new HashMap<String, Object>();
			sessionFactoryParameters.put("request", request);
			sessionFactoryParameters.put("response", response);
			sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
			sessionDAOFactory.beginTransaction();
			
			UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
			loggedUser = sessionUserDAO.findLoggedUser();
			
			daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
			daoFactory.beginTransaction();
			
			commonView(daoFactory, sessionDAOFactory, request);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "admin/view");
			
			/* Blocco Standard per la gestione degli errori */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	public static void search(HttpServletRequest request, HttpServletResponse response) {
		
		DAOFactory sessionDAOFactory = null;
		DAOFactory daoFactory = null;
		Utente loggedUser;
		
		Logger logger = LogService.getApplicationLogger();
		
		try {
			
			Map sessionFactoryParameters = new HashMap<String, Object>();
			sessionFactoryParameters.put("request", request);
			sessionFactoryParameters.put("response", response);
			sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
			sessionDAOFactory.beginTransaction();
			
			UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
			loggedUser = sessionUserDAO.findLoggedUser();
			
			daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
			daoFactory.beginTransaction();
			
			commonView(daoFactory, sessionDAOFactory, request);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "admin/view");
			
			/* Blocco Standard per la gestione degli errori */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	private static void commonView(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request) {
		
		List<Utente> utenti = null;
		UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
		String search = request.getParameter("search");
		
		if (search!=null) {
			utenti = utenteDAO.findByString(search);
		}
		else {
			utenti = utenteDAO.findAll();
		}
		request.setAttribute("utenti", utenti);
	}
}