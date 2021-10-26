package model.dao.mySQLJDBCImpl;

import java.sql.*;

import model.dao.*;
import model.dao.exception.DuplicatedObjectException;
import model.mo.*;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;



public class OrdineDAOMySQLJDBCImpl implements OrdineDAO {
	
	private final String COUNTER_ID = "orderID";
	Connection conn;
	String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	public OrdineDAOMySQLJDBCImpl(Connection conn) { this.conn = conn; }
	
	
	@Override
	public Ordine create(
			Utente utente,
			String destinatario,
			String indirizzo,
			BigDecimal totale,
			String IDpagamento,
			String intestatario) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Ordine ordine = new Ordine();
		ordine.setUser(utente);
		ordine.setDestinatario(destinatario);
		ordine.setIndirizzo(indirizzo);
		ordine.setTotale(totale);
		ordine.setIDpagamento(IDpagamento);
		ordine.setIntestatario(intestatario);
		
		try {
			
			String sql
					= " SELECT orderID "
					+ " FROM ordine "
					+ " WHERE "
					+ " deleted = 'N' AND "
					+ " userID = ? AND "
					+ " data = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utente.getUserID());
			ps.setString(2, timeStamp);
			
			
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
			
			ordine.setOrderID(resultSet.getInt("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO ordine " +
					" ( orderID, userID, destinatario, indirizzo, totale, IDpagamento, intestatario, deleted ) " +
					" VALUES (?,?,?,?,?,?,?,'N')";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, ordine.getOrderID());
			ps.setInt(i++, ordine.getUser().getUserID());
			ps.setString(i++, ordine.getDestinatario());
			ps.setString(i++, ordine.getIndirizzo());
			ps.setBigDecimal(i++, ordine.getTotale());
			ps.setString(i++, ordine.getIDpagamento());
			ps.setString(i++, ordine.getIntestatario());
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
			ps.setInt(i++, ordine.getUser().getUserID());
			ps.setString(i++, ordine.getIndirizzo());
			ps.setTimestamp(i++, ordine.getData());
			ps.setString(i++, ordine.getIDpagamento());
			ps.setInt(i++, ordine.getOrderID());
			
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
					+ " totale = ?, "
					+ " IDpagamento = ? "
					+ " WHERE "
					+ " orderID = ? ";
			
			ps = conn.prepareStatement(sql);
			i = 1;
			ps.setString(i++, ordine.getIndirizzo());
			ps.setTimestamp(i++, ordine.getData());
			ps.setBigDecimal(i++, ordine.getTotale());
			ps.setString(i++, ordine.getIDpagamento());
			ps.setInt(i++, ordine.getOrderID());
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
				ps.setInt(1, ordine.getOrderID());
				ps.executeUpdate();
				ps.close();
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public Ordine findByOrderID(int orderID) {
		
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
			ps.setInt(1, orderID);
			
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
	public List<Ordine> findByUserID(int userID) {
		
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
			ps.setInt(1, userID);
			
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
		
		Utente utente = new Utente();
		ordine.setUser(utente);
		try {
			ordine.setOrderID(rs.getInt("orderID"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.getUser().setUserID(rs.getInt("userID"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setDestinatario(rs.getString("destinatario"));
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
			ordine.setIntestatario(rs.getString("intestatario"));
		} catch (SQLException sqle) {
		}
		try {
			ordine.setDeleted(rs.getString("deleted").equals("Y"));
		} catch (SQLException sqle) {
		}
		
		return ordine;
	}
		
}
