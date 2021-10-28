package controller;

import model.dao.*;
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
			
			commonView(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "orders/view");
			
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
	
	private static void commonView(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request, Utente loggedUser) {
		
		List<Ordine> ordini;
		OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
		ordini = ordineDAO.findByUserID(loggedUser.getUserID());
		request.setAttribute("ordini",ordini);
	}
	
}
