package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.CarrelloDAO;
import model.dao.DettaglioDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Carrello;
import model.mo.Prodotto;
import model.mo.Utente;

import java.util.ArrayList;
import java.util.List;

public class CarrelloDAOMySQLJDBCImpl implements CarrelloDAO {
	
	private final String COUNTER_ID = "cartID";
	Connection conn;
	
	public CarrelloDAOMySQLJDBCImpl(Connection conn) { this.conn = conn; }
	
	
	@Override
	public Carrello create(
			int cartID,
			Utente utente,
			Prodotto prodotto,
			int quantità) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Carrello carrello = new Carrello();
		carrello.setCartID(cartID);
		carrello.setUser(utente);
		carrello.setProduct(prodotto);
		carrello.setQuantità(quantità);
		
		try {
			
			String sql
					= " SELECT cartID "
					+ " FROM carrello "
					+ " WHERE "
					+ " userID = ? AND "
					+ " productID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, carrello.getUser().getUserID());
			ps.setInt(2, carrello.getProduct().getProductID());
			
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("DettaglioDAOMySQLJDBCImpl.create: Tentativo di inserimento di un elemento già esistente.");
			}
			
			/* Eseguo il lock per evitare transazioni concorrenti */
			sql = "UPDATE counter SET counterValue=counterValue+1 WHERE counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.next();
			
			carrello.setCartID(resultSet.getInt("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO carrello " +
					" (cartID, userID, productID, quantità) " +
					" VALUES (?,?,?,?) ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, carrello.getCartID());
			ps.setInt(i++, carrello.getUser().getUserID());
			ps.setInt(i++, carrello.getProduct().getProductID());
			ps.setInt(i++, carrello.getQuantità());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return carrello;
	}
	
	@Override
	public void update(Carrello carrello) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " SELECT cartID "
					+ " FROM carrello "
					+ " WHERE productID = ? AND "
					+ " userID = ? AND "
					+ " quantità = ? AND "
					+ " cartID <> ? ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, carrello.getProduct().getProductID());
			ps.setInt(i++, carrello.getUser().getUserID());
			ps.setInt(i++, carrello.getQuantità());
			ps.setInt(i++, carrello.getCartID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("DettaglioDAOMySQLJDBCImpl.update: Tentativo di aggiornamento in un elemento già esistente.");
			}
			
			sql
					= " UPDATE carrello "
					+ " SET "
					+ " userID = ?, "
					+ " productID = ?, "
					+ " quantità = ? "
					+ " WHERE cartID = ? ";
			
			ps = conn.prepareStatement(sql);
			i = 1;
			ps.setInt(i++, carrello.getUser().getUserID());
			ps.setInt(i++, carrello.getProduct().getProductID());
			ps.setInt(i++, carrello.getQuantità());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void delete(Carrello carrello) {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " UPDATE carrello"
					+ " SET deleted = 'Y' "
					+ " WHERE "
					+ " cartID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, carrello.getCartID());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public List<Carrello> findByUserID(int userID) {
		
		PreparedStatement ps;
		Carrello carrello = null;
		List<Carrello> carrelli = new ArrayList<>();
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM carrello "
					+ " WHERE "
					+ " userID = ?"
					+ " AND deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, carrello.getUser().getUserID());
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				carrello = read(resultSet);
				carrelli.add(carrello);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return carrelli;
	}
	
	Carrello read(ResultSet rs) {
		
		Carrello carrello = new Carrello();
		try {
			carrello.setCartID(rs.getInt("cartID"));
		} catch (SQLException sqle) {
		}
		try {
			carrello.getUser().setUserID(rs.getInt("userID"));
		} catch (SQLException sqle) {
		}
		try {
			carrello.getProduct().setProductID(rs.getInt("productID"));
		} catch (SQLException sqle) {
		}
		try {
			carrello.setQuantità(rs.getInt("quantità"));
		} catch (SQLException sqle) {
		}
		try {
			carrello.setDeleted(rs.getString("deleted").equals("Y"));
		} catch (SQLException sqle) {
		}
		
		return carrello;
	}
	
}
