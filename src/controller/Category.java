package controller;

import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.dao.mySQLJDBCImpl.CarrelloDAOMySQLJDBCImpl;
import model.mo.*;
import services.config.Configuration;
import services.log.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Category {
	
	private Category() {
	}
	
	public static void view(HttpServletRequest request, HttpServletResponse response) {
		
		DAOFactory sessionDAOFactory = null;
		DAOFactory daoFactory = null;
		Utente loggedUser;
		String applicationMessage = null;
		
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
			request.setAttribute("viewUrl", "category/view");
			
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
		String applicationMessage = null;
		
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
			request.setAttribute("viewUrl", "category/view");
			
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
	
	public static void addToCart(HttpServletRequest request, HttpServletResponse response) {
		
		DAOFactory sessionDAOFactory = null;
		DAOFactory daoFactory = null;
		Utente loggedUser;
		String applicationMessage = null;
		
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
			
			CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
			int productID = Integer.parseInt(request.getParameter("productID"));
			
			UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
			Utente utente = utenteDAO.findByUsername(loggedUser.getUsername());
			
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			Prodotto prodotto = prodottoDAO.findByProductId(productID);
			
			try {
				carrelloDAO.create(
						loggedUser,
						prodotto,
						1);
				
			} catch (DuplicatedObjectException e) {
				applicationMessage = "Prodotto già inserito nel carrello";
				logger.log(Level.INFO, "Tentativo di inserimento di prodotto già nel carrello");
			}
			
			commonView(daoFactory, sessionDAOFactory, request);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("applicationMessage", applicationMessage);
			request.setAttribute("viewUrl", "category/view");
			
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
			
			List<Prodotto> prodotti;
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			String search = request.getParameter("search");
			
			if (search!=null) {
				prodotti = prodottoDAO.findByString(search);
			}
			else {
				prodotti = prodottoDAO.findAll();
			}
			request.setAttribute("prodotti", prodotti);
	}
	
	private static void  createItem(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request, Utente loggedUser) {
		
	
	
		
	}
}