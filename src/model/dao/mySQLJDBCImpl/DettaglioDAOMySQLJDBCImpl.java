package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.DettaglioDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Dettaglio;
import model.mo.Ordine;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DettaglioDAOMySQLJDBCImpl implements DettaglioDAO {
	
	private final String COUNTER_ID = "detailID";
	Connection conn;
	
	public DettaglioDAOMySQLJDBCImpl(Connection conn) { this.conn = conn; }
	
	
	@Override
	public Dettaglio create(
			Long detailID,
			Ordine ordine,
			Prodotto prodotto,
			Long quantità,
			BigDecimal subtotale) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Dettaglio dettaglio = new Dettaglio();
		dettaglio.setDetailID(detailID);
		dettaglio.setOrder(ordine);
		dettaglio.setProduct(prodotto);
		dettaglio.setQuantità(quantità);
		dettaglio.setSubtotale(subtotale);
		
		try {
			
			String sql
					= " SELECT detailID "
					+ " FROM dettagliOrdine "
					+ " WHERE "
					+ " orderID = ? AND "
					+ " productID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, dettaglio.getOrder().getOrderID());
			ps.setLong(2, dettaglio.getProduct().getProductID());
			
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("DettaglioDAOMySQLJDBCImpl.create: Tentativo di inserimento di un dettaglio già esistente.");
			}
			
			/* Eseguo il lock per evitare transazioni concorrenti */
			sql = "UPDATE counter SET counterValue=counterValue+1 WHERE counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.next();
			
			dettaglio.setDetailID(resultSet.getLong("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO dettagliOrdine " +
					" (detailID, orderID, productID, quantità, subtotale) " +
					" VALUES (?,?,?,?,?) ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, dettaglio.getDetailID());
			ps.setLong(i++, dettaglio.getOrder().getOrderID());
			ps.setLong(i++, dettaglio.getProduct().getProductID());
			ps.setLong(i++, dettaglio.getQuantità());
			ps.setBigDecimal(i++, dettaglio.getSubtotale());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return dettaglio;
	}
	
	@Override
	public void update(Dettaglio dettaglio) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " SELECT detailID "
					+ " FROM dettagliOrdine "
					+ " WHERE productID = ? AND "
					+ " orderID = ? AND "
					+ " quantità = ? AND "
					+ " detailID <> ? ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, dettaglio.getProduct().getProductID());
			ps.setLong(i++, dettaglio.getOrder().getOrderID());
			ps.setLong(i++, dettaglio.getQuantità());
			ps.setLong(i++, dettaglio.getDetailID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("DettaglioDAOMySQLJDBCImpl.update: Tentativo di aggiornamento in un dettaglio già esistente.");
			}
			
			sql
					= " UPDATE dettagliOrdine "
					+ " SET "
					+ " orderID = ?, "
					+ " productID = ?, "
					+ " quantità = ?,"
					+ " subtotale = ?"
					+ " WHERE detailID = ? ";
			
			ps = conn.prepareStatement(sql);
			i = 1;
			ps.setLong(i++, dettaglio.getOrder().getOrderID());
			ps.setLong(i++, dettaglio.getProduct().getProductID());
			ps.setLong(i++, dettaglio.getQuantità());
			ps.setBigDecimal(i++, dettaglio.getSubtotale());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public void delete(Dettaglio dettaglio) {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " DELETE "
					+ " FROM dettagliOrdine "
					+ " WHERE "
					+ " detailID = ? ";
					
			ps = conn.prepareStatement(sql);
			ps.setLong(1, dettaglio.getDetailID());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public Dettaglio findByDetailID(Long detailID) {
		
		PreparedStatement ps;
		Dettaglio dettaglio = null;
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM dettagliOrdine "
					+ " WHERE "
					+ " detailID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, detailID);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				dettaglio = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return dettaglio;
	}
	
	@Override
	public List<Dettaglio> findByOrderID(Long orderID) {
		
		PreparedStatement ps;
		Dettaglio dettaglio = null;
		List<Dettaglio> dettagli = new ArrayList<Dettaglio>(20);
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM dettagliOrdine "
					+ " WHERE "
					+ " orderID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderID);
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				dettaglio = read(resultSet);
				dettagli.add(dettaglio);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return dettagli;
	}
	
	@Override
	public List<Dettaglio> findByProductID(Long orderID) {
		
		PreparedStatement ps;
		Dettaglio dettaglio = null;
		List<Dettaglio> dettagli = new ArrayList<Dettaglio>(20);
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM dettagliOrdine "
					+ " WHERE "
					+ " productID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderID);
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				dettaglio = read(resultSet);
				dettagli.add(dettaglio);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return dettagli;
	}
	
	Dettaglio read(ResultSet rs) {
		
		Dettaglio dettaglio = new Dettaglio();
		try {
			dettaglio.setDetailID(rs.getLong("detailID"));
		} catch (SQLException sqle) {
		}
		try {
			dettaglio.getOrder().setOrderID(rs.getLong("orderID"));
		} catch (SQLException sqle) {
		}
		try {
			dettaglio.getProduct().setProductID(rs.getLong("productID"));
		} catch (SQLException sqle) {
		}
		try {
			dettaglio.setQuantità(rs.getLong("quantità"));
		} catch (SQLException sqle) {
		}
		try {
			dettaglio.setSubtotale(rs.getBigDecimal("subtotale"));
		} catch (SQLException sqle) {
		}
		
		return dettaglio;
	}
	
}
