package controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Carrello;
import model.mo.Ordine;
import model.mo.Prodotto;
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
			
			List<Carrello> carrelli;
			CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
			carrelli = carrelloDAO.findByUserID(loggedUser.getUserID());
			request.setAttribute("carrelli", carrelli);
			String s = "checkout";
			int i = 0;
			
			/* Blocco per la gestione di prodotti già aggiunti al carrello ma non più disponibili in fase di checkout */
			
			while (i<carrelli.size() && s.equals("checkout")){
				
				ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
				Prodotto prodotto = prodottoDAO.findByProductId(carrelli.get(i).getProduct().getProductID());
				if(carrelli.get(i).getQuantita() > prodotto.getMagazzino()) {
					s = "view";
					applicationMessage = prodotto.getNomeProdotto() + " è disponibile nelle seguenti quantità : " + prodotto.getMagazzino();
				}
				i++;
			}
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("applicationMessage", applicationMessage);
			request.setAttribute("viewUrl", "cart/"+s);
			
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
			
			/* Blocco di inserimento ordine */
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
				
				/* Blocco di gestione magazzino */
				
				List<Carrello> carrelli;
				CarrelloDAO carrelloDAO = daoFactory.getCarrelloDAO();
				carrelli = carrelloDAO.findByUserID(loggedUser.getUserID());
				for (int i = 0; i<carrelli.size(); i++)
				{
					Carrello carrello = carrelli.get(i);
					ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
					Prodotto prodotto = prodottoDAO.findByProductId(carrello.getProduct().getProductID());
					int magazzino = prodotto.getMagazzino() - carrello.getQuantita();
					prodotto.setMagazzino(magazzino);
					prodottoDAO.update(prodotto);
					carrelloDAO.delete(carrello.getCartID());
				}
				
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
	
	public static void update(HttpServletRequest request, HttpServletResponse response) {
		
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
