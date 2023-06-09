package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.ProdottoDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProdottoDAOMySQLJDBCImpl implements ProdottoDAO {
	
	private final String COUNTER_ID = "productID";
	Connection conn;
	
	public ProdottoDAOMySQLJDBCImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Prodotto create(
			String nome,
			String marchio,
			String categoria,
			String descrizione,
			int magazzino,
			BigDecimal prezzoKg,
			BigDecimal peso) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		Prodotto prodotto = new Prodotto();
		prodotto.setNomeProdotto(nome);
		prodotto.setMarchio(marchio);
		prodotto.setCategoria(categoria);
		prodotto.setDescrizione(descrizione);
		prodotto.setMagazzino(magazzino);
		prodotto.setPrezzoKg(prezzoKg);
		prodotto.setPeso(peso);
		
		try {
			
			String sql
					= " SELECT productID "
					+ " FROM prodotto "
					+ " WHERE "
					+ " nome = ? AND "
					+ " marchio = ? AND "
					+ " categoria = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodotto.getNomeProdotto());
			ps.setString(2, prodotto.getMarchio());
			ps.setString(3, prodotto.getCategoria());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("ProdottoDAOMySQLJDBCImpl.create: Tentativo di inserimento di un prodotto già esistente.");
			}
			
			/* Eseguo il lock per evitare transazioni concorrenti */
			sql = "UPDATE counter SET counterValue=counterValue+1 WHERE counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";
			
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.next();
			
			prodotto.setProductID(resultSet.getInt("counterValue"));
			
			resultSet.close();
			
			sql =   " INSERT INTO prodotto " +
					" ( productID, nome, marchio, categoria, descrizione, magazzino, prezzoKg, peso ) " +
					" VALUES (?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, prodotto.getProductID());
			ps.setString(i++, prodotto.getNomeProdotto());
			ps.setString(i++, prodotto.getMarchio());
			ps.setString(i++, prodotto.getCategoria());
			ps.setString(i++, prodotto.getDescrizione());
			ps.setInt(i++, prodotto.getMagazzino());
			ps.setBigDecimal(i++, prodotto.getPrezzoKg());
			ps.setBigDecimal(i++, prodotto.getPeso());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotto;
	}
	
	@Override
	public void update(Prodotto prodotto) throws DuplicatedObjectException {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " SELECT productID"
					+ " FROM prodotto"
					+ " WHERE"
					+ " nome = ? AND"
					+ " marchio = ? AND"
					+ " categoria = ? AND"
					+ " productID <> ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodotto.getNomeProdotto());
			ps.setString(2, prodotto.getMarchio());
			ps.setString(3, prodotto.getCategoria());
			ps.setInt(4, prodotto.getProductID());
			
			ResultSet resultSet = ps.executeQuery();
			
			boolean exist;
			exist = resultSet.next();
			resultSet.close();
			
			if (exist) {
				throw new DuplicatedObjectException("ProdottoDAOMySQLJDBCImpl.update: Tentativo di aggiornamento in un prodotto già esistente.");
			}
			
			sql
					= " UPDATE prodotto "
					+ " SET "
					+ " nome = ?, "
					+ " marchio = ?, "
					+ " categoria = ?, "
					+ " descrizione = ?, "
					+ " magazzino = ?, "
					+ " prezzoKg = ?, "
					+ " peso = ? "
					+ " WHERE "
					+ " productID = ? ";
					
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setString(i++, prodotto.getNomeProdotto());
			ps.setString(i++, prodotto.getMarchio());
			ps.setString(i++, prodotto.getCategoria());
			ps.setString(i++, prodotto.getDescrizione());
			ps.setInt(i++, prodotto.getMagazzino());
			ps.setBigDecimal(i++, prodotto.getPrezzoKg());
			ps.setBigDecimal(i++, prodotto.getPeso());
			ps.setInt(i++, prodotto.getProductID());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public void delete(Prodotto prodotto) {
		
		PreparedStatement ps;
		
		try {
			
			String sql
					= " UPDATE prodotto "
					+ " SET magazzino = 0 "
					+ " WHERE "
					+ " productID = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, prodotto.getProductID());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override
	public int checkAvailability(int productID) {
		
		PreparedStatement ps;
		Prodotto prodotto = null;
		try {
			
			String sql
					= " SELECT magazzino "
					+ " FROM prodotto "
					+ " WHERE productID = ? ";
					
			ps = conn.prepareStatement(sql);
			ps.setInt(1, productID);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				prodotto = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotto.getMagazzino();
	}
	
	@Override
	public Prodotto findByProductId(int productID) {
		
		PreparedStatement ps;
		Prodotto prodotto = null;
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM prodotto "
					+ " WHERE "
					+ " productID = ? ";
					
			ps = conn.prepareStatement(sql);
			ps.setInt(1, productID);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				prodotto = read(resultSet);
			}
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotto;
	}
	
	@Override
	public List<Prodotto> findByCategory(String category) {
		
		PreparedStatement ps;
		Prodotto prodotto = null;
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM prodotto "
					+ " WHERE "
					+ " categoria = ? ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, category);
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				prodotto = read(resultSet);
				prodotti.add(prodotto);
			}
			
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotti;
	}
	
	
	@Override
	public List<Prodotto> findByString(String searchString) {
		
		PreparedStatement ps;
		Prodotto prodotto = null;
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM prodotto "
					+ " WHERE "
					+ " ( nome LIKE ? OR marchio LIKE ? OR categoria LIKE ? ) "
					+ " ORDER BY categoria, nome ";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, "%" + searchString + "%");
					ps.setString(2, "%" + searchString + "%");
					ps.setString(3, "%" + searchString + "%");
					
					ResultSet resultSet = ps.executeQuery();
					
					while (resultSet.next()) {
						prodotto = read(resultSet);
						prodotti.add(prodotto);
					}
					
					resultSet.close();
					ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotti;
	}
	
	public List<Prodotto> findAll() {
		
		PreparedStatement ps;
		Prodotto prodotto;
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		
		try {
			
			String sql
					= " SELECT * "
					+ " FROM prodotto ";
			
			ps = conn.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				prodotto = read(resultSet);
				prodotti.add(prodotto);
			}
			
			resultSet.close();
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return prodotti;
		
	}
	
	Prodotto read(ResultSet rs) {
		
		Prodotto prodotto = new Prodotto();
		try {
			prodotto.setProductID(rs.getInt("productID"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setNomeProdotto(rs.getString("nome"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setMarchio(rs.getString("marchio"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setCategoria(rs.getString("categoria"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setDescrizione(rs.getString("descrizione"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setMagazzino(rs.getInt("magazzino"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setPrezzoKg(rs.getBigDecimal("prezzoKg"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setPeso(rs.getBigDecimal("peso"));
		} catch (SQLException sqle) {
		}
		try {
			prodotto.setPrezzoTot(rs.getBigDecimal("prezzoTot"));
		} catch (SQLException sqle) {
		}
		
		return prodotto;
	}
	
}
