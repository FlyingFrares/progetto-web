package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Utente;
import model.dao.UtenteDAO;

public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {
	
	private final String COUNTER_ID = "userID";
	Connection conn;
	
	public UtenteDAOMySQLJDBCImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Utente create(
			String username,
			String admin,
			String password,
			String nome,
			String cognome,
			String email) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Utente utente = new Utente();
		utente.setUsername(username);
		utente.setAdmin(admin);
		utente.setPassword(password);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		
		try {
			
			String sql
					= " SELECT userID "
					+ " FROM utente "
					+ " WHERE "
					+ " deleted ='N' AND "
					+ " username = ? AND "
					+ " email = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, utente.getUsername());
			ps.setString(2, utente.getEmail());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("UtenteDAOMySQLJDBCImpl.create: Tentativo di inserimento di un utente già esistente.");
			}
			
			/* Eseguo il lock per evitare transazioni concorrenti */
			sql = "UPDATE counter SET counterValue=counterValue+1 WHERE counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.next();
			
			utente.setUserID(resultSet.getInt("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO utente " +
					" ( userID, username, admin, password, nome, cognome, email, deleted )" +
					" VALUES (?,?,?,?,?,?,?,'N') ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, utente.getUserID());
			ps.setString(i++, utente.getUsername());
			ps.setString(i++, utente.getAdmin());
			ps.setString(i++, utente.getPassword());
			ps.setString(i++, utente.getNome());
			ps.setString(i++, utente.getCognome());
			ps.setString(i++, utente.getEmail());
			ps.executeUpdate();
			
		}  catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return utente;
	}
	
	@Override
	public void update(Utente utente) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " SELECT userID "
					+ " FROM utente "
					+ " WHERE "
					+ " deleted ='N' AND "
					+ " username = ? AND "
					+ " email = ? AND "
					+ " userID <> ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, utente.getUsername());
			ps.setString(2, utente.getEmail());
			ps.setInt(3, utente.getUserID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("UtenteDAOMySQLJDBCImpl.update: Tentativo di aggiornamento in un utente già esistente.");
			}
			
			sql
					= " UPDATE utente "
					+ " SET "
					+ " admin = ?, "
					+ " password = ?, "
					+ " nome = ?, "
					+ " cognome = ?, "
					+ " email = ? "
					+ " WHERE "
					+ " userID = ? ";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setString(i++, utente.getAdmin());
			ps.setString(i++, utente.getPassword());
			ps.setString(i++, utente.getNome());
			ps.setString(i++, utente.getCognome());
			ps.setString(i++, utente.getEmail());
			ps.setString(i++, utente.getUsername());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void delete(Utente utente) {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " UPDATE utente "
					+ " SET deleted ='Y' "
					+ " WHERE "
					+ " userID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, utente.getUserID());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Utente findLoggedUser() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	@Override
	public Utente findByUsername(String username) {
		
		PreparedStatement ps;
		Utente utente = null;
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM utente "
					+ " WHERE "
					+ " username = ?"
					+ " AND deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				utente = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return utente;
	}
	
	@Override
	public Utente findByEmail(String email) {
		
		PreparedStatement ps;
		Utente utente = null;
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM utente "
					+ " WHERE "
					+ " email = ? AND "
					+ " deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				utente = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return utente;
	}
	
	@Override
	public List<Utente> findAll() {
		
		PreparedStatement ps;
		List<Utente> utenti = new ArrayList<Utente>(20);
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM utente "
					+ " WHERE "
					+ " deleted = 'N' ";
			
			ps = conn.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Utente u = read(resultSet);
				utenti.add(u);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return utenti;
	}
	
	Utente read(ResultSet rs) {
		
		Utente utente = new Utente();
		try {
			utente.setUserID(rs.getInt("userID"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setUsername(rs.getString("username"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setAdmin(rs.getString("admin"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setPassword(rs.getString("password"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setNome(rs.getString("nome"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setCognome(rs.getString("cognome"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setEmail(rs.getString("email"));
		} catch (SQLException sqle) {
		}
		try {
			utente.setDeleted(rs.getString("deleted").equals("Y"));
		} catch (SQLException sqle) {
		}
		
		return utente;
	}
	
}
