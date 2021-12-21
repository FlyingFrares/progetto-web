package controller;

import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Ordine;
import model.mo.Utente;
import services.config.Configuration;
import services.log.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Orders {
	
	private Orders() {
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
			
			commonView(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/view");
			
			/* Blocco Standard per la gestione degli errori: Cookie + DB */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (daoFactory != null) daoFactory.rollbackTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (daoFactory != null) daoFactory.closeTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	public static void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			int orderID = Integer.parseInt(request.getParameter("orderID"));
			OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
			ordineDAO.delete(orderID);
			
			commonViewAdmin(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/admin/view");
			
			/* Blocco Standard per la gestione degli errori: Cookie + DB */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (daoFactory != null) daoFactory.rollbackTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (daoFactory != null) daoFactory.closeTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	public static void adminView(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			commonViewAdmin(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/admin/view");
			
			/* Blocco Standard per la gestione degli errori: Cookie + DB */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (daoFactory != null) daoFactory.rollbackTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (daoFactory != null) daoFactory.closeTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	public static void modifyOrderView(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			int orderID = Integer.parseInt(request.getParameter("orderID"));
			OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
			Ordine ordine = ordineDAO.findByOrderID(orderID);
			request.setAttribute("ordine", ordine);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/admin/modifyOrder");
			
			/* Blocco Standard per la gestione degli errori: Cookie + DB */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (daoFactory != null) daoFactory.rollbackTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (daoFactory != null) daoFactory.closeTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	public static void modifyOrder(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			String destinatario = request.getParameter("name");
			String indirizzo = request.getParameter("address");
			String stato = request.getParameter("stato");
			int orderID = Integer.parseInt(request.getParameter("orderID"));
			
			OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
			Ordine ordine = ordineDAO.findByOrderID(orderID);
			if(!destinatario.equals("")) {
				ordine.setDestinatario(destinatario);
			}
			if(!indirizzo.equals("")) {
				ordine.setIndirizzo(indirizzo);
			}
			if(stato != null)
			{
				ordine.setStato(stato);
			}
			
			try {
				ordineDAO.update(ordine);
				
			}catch (DuplicatedObjectException e) {
				logger.log(Level.INFO, "Errore Modifica Ordine");
			}
			
			commonViewAdmin(daoFactory, sessionDAOFactory, request, ordine.getUser());
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/admin/view");
			
			/* Blocco Standard per la gestione degli errori: Cookie + DB */
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Controller Error", e);
			try {
				if (daoFactory != null) daoFactory.rollbackTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
			} catch (Throwable t) {
			}
			throw new RuntimeException(e);
			
		} finally {
			try {
				if (daoFactory != null) daoFactory.closeTransaction();
				if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
			} catch (Throwable t) {
			}
		}
		
	}
	
	private static void commonView(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request, Utente loggedUser) {
		
		List<Ordine> ordini;
		OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
		ordini = ordineDAO.findByUserID(loggedUser.getUserID());
		request.setAttribute("ordini",ordini);
	}
	
	private static void commonViewAdmin(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request, Utente user) {
		
		List<Utente> utenti;
		UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
		utenti = utenteDAO.findAll();
		request.setAttribute("utenti",utenti);
		
		
		String userID = request.getParameter("userID");
		if(userID == null) {
			commonView(daoFactory, sessionDAOFactory, request, user);
			request.setAttribute("utente",user);
		}
		else {
			
			UtenteDAO utenteOrdiniDAO = daoFactory.getUtenteDAO();
			Utente utenteOrdini = utenteOrdiniDAO.findByUserID(Integer.parseInt(userID));
			List<Ordine> ordini;
			OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
			ordini = ordineDAO.findByUserID(Integer.parseInt(userID));
			request.setAttribute("ordini", ordini);
			request.setAttribute("utente",utenteOrdini);
		}
	}
}
