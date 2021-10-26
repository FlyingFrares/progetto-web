package controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Carrello;
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

public class Cart {
	
	private Cart() {
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
			request.setAttribute("viewUrl", "cart/view");
			
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
	
	public static void checkoutView(HttpServletRequest request, HttpServletResponse response) {
		
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
			request.setAttribute("viewUrl", "cart/checkout");
			
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
	
	public static void confirm(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			String destinatario = request.getParameter("name");
			String via = request.getParameter("address");
			String citta = request.getParameter("city");
			String cap = request.getParameter("cap");
			String indirizzo = via+", "+cap+", "+citta;
			String intestatario = request.getParameter("cardname");
			String IDpagamento = request.getParameter("cardnumber");
			BigDecimal totale = new BigDecimal(request.getParameter("total"));
			
			OrdineDAO ordineDAO = daoFactory.getOrdineDAO();
			
			try {
				ordineDAO.create(
						loggedUser,
						destinatario,
						indirizzo,
						totale,
						IDpagamento,
						intestatario);
				
				applicationMessage = "Ordine confermato";
				
			}catch (DuplicatedObjectException e) {
				applicationMessage = "Ordine non confermato";
				logger.log(Level.INFO, "Errore Ordine");
			}
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("applicationMessage", applicationMessage);
			request.setAttribute("viewUrl", "home/view");
			
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
	
	
	public static void modify(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			int qty = Integer.parseInt(request.getParameter("qty"));
			int cartID = Integer.parseInt(request.getParameter("cartID"));
			CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
			Carrello carrello = carrelloDAO.findByCartId(cartID);
			carrello.setQuantita(qty);
			carrelloDAO.update(carrello);
			
			commonView(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "cart/view");
			
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
	
	public static void removeItem(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			int cartID = Integer.parseInt(request.getParameter("cartID"));
			CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
			carrelloDAO.delete(cartID);
			
			commonView(daoFactory, sessionDAOFactory, request, loggedUser);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "cart/view");
			
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
		
		List<Carrello> carrelli;
		CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
		carrelli = carrelloDAO.findByUserID(loggedUser.getUserID());
		request.setAttribute("carrelli", carrelli);
	}
}
