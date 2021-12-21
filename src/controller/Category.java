package controller;

import com.sun.xml.internal.ws.api.BindingID;
import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.dao.mySQLJDBCImpl.CarrelloDAOMySQLJDBCImpl;
import model.mo.*;
import services.config.Configuration;
import services.log.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
			request.setAttribute("viewUrl", "category/view");
			
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
			
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			Prodotto prodotto = prodottoDAO.findByProductId(productID);
			
			try {
				carrelloDAO.create(
						loggedUser,
						prodotto,
						1);
				
				applicationMessage = "Aggiunto al carrello";
				
			}catch (DuplicatedObjectException e) {
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
	
	public static void modifyProductView(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			int productID = Integer.parseInt(request.getParameter("productID"));
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			Prodotto prodotto = prodottoDAO.findByProductId(productID);
			request.setAttribute("prodotto", prodotto);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "category/admin/modifyProduct");
			
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
	
	public static void modifyProduct(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			String nome = request.getParameter("name");
			String marchio = request.getParameter("brand");
			String categoria = request.getParameter("category");
			String magazzino = request.getParameter("magazine");
			String prezzo = request.getParameter("price");
			String peso = request.getParameter("weight");
			int productID = Integer.parseInt(request.getParameter("productID"));
			
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			Prodotto prodotto = prodottoDAO.findByProductId(productID);
			
			if (!nome.equals("")) {
				prodotto.setNomeProdotto(nome);
			}
			if (!marchio.equals("")) {
				prodotto.setMarchio(marchio);
			}
			if (!categoria.equals("")) {
				prodotto.setCategoria(categoria);
			}
			if (!magazzino.equals(""))
			{
				prodotto.setMagazzino(Integer.parseInt(magazzino));
			}
			if (!prezzo.equals("")) {
				
				BigDecimal price = new BigDecimal(prezzo);
				prodotto.setPrezzoKg(price);
			}
			if (!peso.equals("")) {
				
				BigDecimal weight = new BigDecimal(peso);
				prodotto.setPeso(weight);
			}
			
			try {
				prodottoDAO.update(prodotto);
				
			}catch (DuplicatedObjectException e) {
				logger.log(Level.INFO, "Errore Modifica Prodotto");
			}
			
			commonView(daoFactory, sessionDAOFactory, request);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "category/view");
			
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
	
	public static void createProductView(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "category/admin/modifyProduct");
			
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
	
	public static void createProduct(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			String nome = request.getParameter("name");
			String marchio = request.getParameter("brand");
			String categoria = request.getParameter("category");
			int magazzino = Integer.parseInt(request.getParameter("magazine"));
			BigDecimal prezzo = new BigDecimal(request.getParameter("price"));
			BigDecimal peso = new BigDecimal(request.getParameter("weight"));
			String descrizione = request.getParameter("description");
			
			ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
			
			try {
				prodottoDAO.create(
						nome,
						marchio,
						categoria,
						descrizione,
						magazzino,
						prezzo,
						peso);
				
			}catch (DuplicatedObjectException e) {
				logger.log(Level.INFO, "Errore Modifica Prodotto");
			}
			
			commonView(daoFactory, sessionDAOFactory, request);
			
			daoFactory.commitTransaction();
			sessionDAOFactory.commitTransaction();
			
			request.setAttribute("loggedOn", loggedUser != null);
			request.setAttribute("loggedUser", loggedUser);
			request.setAttribute("viewUrl", "category/view");
			
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
}