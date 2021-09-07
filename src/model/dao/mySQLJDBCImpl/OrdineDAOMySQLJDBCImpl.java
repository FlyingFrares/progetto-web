package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Ordine;
import model.dao.OrdineDAO;
import model.mo.Utente;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.math.BigDecimal;


public class OrdineDAOMySQLJDBCImpl implements OrdineDAO {
	
	private final String COUNTER_ID = "orderID";
	Connection conn;
	
	public OrdineDAOMySQLJDBCImpl(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public Ordine create(
			Long orderID,
			Utente user,
			String indirizzo,
			Timestamp data,
			String stato,
			BigDecimal totale,
			String IDpagamento) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Ordine ordine = new Ordine();
		ordine.setOrderID(orderID);
		ordine.setUser(user);
		ordine.setIndirizzo(indirizzo);
		ordine.setData(data);
		ordine.setStato(stato);
		ordine.setTotale(totale);
		ordine.setIDpagamento(IDpagamento);
		
		try {
			
			String sql
					= " SELECT orderID "
					+ " FROM ordine "
					+ " WHERE "
					+ " deleted = 'N' AND"
					+ " orderID = ? AND "
					+ " userID = ? ";
					
			ps = conn.prepareStatement(sql);
			ps.setLong(1, ordine.getOrderID());
			ps.setLong(2, ordine.getUser().getUserID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("OrdineDAOMySQLJDBCImpl.create: Tentativo di inserimento di un ordine già esistente.");
			}
			
			/* Eseguo il lock per evitare transazioni concorrenti */
			sql = "UPDATE counter SET counterValue=counterValue+1 WHERE counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.next();
			
			ordine.setOrderID(resultSet.getLong("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO ordine " +
					" ( orderID, userID, indirizzo, data, stato, totale, IDpagamento, deleted ) " +
					" VALUES (?,?,?,?,?,?,?,'N')";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, ordine.getOrderID());
			ps.setLong(i++, ordine.getUser().getUserID());
			ps.setString(i++, ordine.getIndirizzo());
			ps.setTimestamp(i++, ordine.getData());
			ps.setString(i++, ordine.getStato());
			ps.setBigDecimal(i++, ordine.getTotale());
			ps.setString(i++, ordine.getIDpagamento());
			ps.executeUpdate();
			
		}  catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ordine;
	}
	
	
	@Override
	public void update(Ordine ordine) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " SELECT orderID "
					+ " FROM ordine "
					+ " WHERE "
					+ " deleted = 'N' AND "
					+ " userID = ? AND "
					+ " indirizzo = ? AND "
					+ " data = ? AND "
					+ " IDpagamento = ? AND "
					+ " orderID <> ? ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, ordine.getUser().getUserID());
			ps.setString(i++, ordine.getIndirizzo());
			ps.setTimestamp(i++, ordine.getData());
			ps.setString(i++, ordine.getIDpagamento());
			ps.setLong(i++, ordine.getOrderID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("OrdineDAOMySQLJDBCImpl.update: Tentativo di aggiornamento in un ordine già esistente.");
			}
			
			sql
					= " UPDATE ordine "
					+ " SET "
					+ " indirizzo = ?, "
					+ " data = ?, "
					+ " stato = ?, "
					+ " totale = ?, "
					+ " IDpagamento = ? "
					+ " WHERE "
					+ " orderID = ? ";
			
			ps = conn.prepareStatement(sql);
			i = 1;
			ps.setString(i++, ordine.getIndirizzo());
			ps.setTimestamp(i++, ordine.getData());
			ps.setString(i++, ordine.getStato());
			ps.setBigDecimal(i++, ordine.getTotale());
			ps.setString(i++, ordine.getIDpagamento());
			ps.setLong(i++, ordine.getOrderID());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void delete(Ordine ordine) {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " UPDATE ordine "
					+ " SET deleted = 'Y' "
					+ " WHERE "
					+ " orderID = ? ";
					
				ps = conn.prepareStatement(sql);
				ps.setLong(1, ordine.getOrderID());
				ps.executeUpdate();
				ps.close();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public Ordine findByOrderID(Long orderID) {
		
		PreparedStatement ps;
		Ordine ordine = null;
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM ordine "
					+ " WHERE "
					+ " orderID = ? AND "
					+ " deleted = 'N' ";
				
			ps = conn.prepareStatement(sql);
			ps.setLong(1, ordine.getOrderID());
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				ordine = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ordine;
	}
	
	@Override
	public List<Ordine> findByUserID(Long userID) {
		
		PreparedStatement ps;
		Ordine ordine = null;
		List<Ordine> ordini = new ArrayList<Ordine>(20);
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM ordine "
					+ " WHERE "
					+ " userID = ? AND "
					+ " deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, ordine.getUser().getUserID());
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				ordine = read(resultSet);
				ordini.add(ordine);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ordini;
	}
	
	public List<Ordine> findAll() {
		
		PreparedStatement ps;
		List<Ordine> ordini = new ArrayList<Ordine>(40);
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM ordine "
					+ " WHERE "
					+ " deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Ordine ordine = read(resultSet);
				ordini.add(ordine);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ordini;
		
	}
	
	Ordine read(ResultSet rs) {
		
		Ordine ordine = new Ordine();
		try {
			ordine.setOrderID(rs.getLong("orderID"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.getUser().setUserID(rs.getLong("userID"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setIndirizzo(rs.getString("indirizzo"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setData(rs.getTimestamp("data"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setTotale(rs.getBigDecimal("totale"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setIDpagamento(rs.getString("IDpagamento"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setDeleted(rs.getString("deleted").equals("Y"));
		} catch (SQLException sqle) {
		}
		
		return ordine;
	}
		
}
