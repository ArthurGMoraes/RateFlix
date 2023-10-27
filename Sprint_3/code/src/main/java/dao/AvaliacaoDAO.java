package dao;

import model.Avaliacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class AvaliacaoDAO extends DAO {	
	public AvaliacaoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	/*
     * ### CREATE
     */

	public boolean insert(Avaliacao avaliacao) {
		boolean status = false;
		try {
			String sql = "INSERT INTO avaliacao (valor, id_usr) "
		               + "VALUES ("
		               + avaliacao.getValor() + ", " + avaliacao.getId_usr() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

    /*
     *  RETRIEVE
     */
	
	public Avaliacao get(int id) {
		Avaliacao avaliacao = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM avaliacao WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 avaliacao = new Avaliacao(rs.getInt("id"), rs.getInt("valor"),  rs.getInt("id_usr"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return avaliacao;
	}
	
	public List<Avaliacao> get() {
		return get("");
	}
	
	public List<Avaliacao> getOrderByID() {
		return get("id");		
	}

    public List<Avaliacao> getOrderByValor() {
		return get("valor");		
	}

    public List<Avaliacao> getOrderByIDusr() {
		return get("id_usr");		
	}
	
	private List<Avaliacao> get(String orderBy) {
		List<Avaliacao> discussoes = new ArrayList<Avaliacao>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM avaliacao" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Avaliacao p = new Avaliacao(rs.getInt("id"), rs.getInt("valor"),  rs.getInt("id_usr"));
	            discussoes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return discussoes;
	}

    /*
     * ### UPDATE
     */

     public boolean update(Avaliacao avaliacao) {
		boolean status = false;
		try {  
			String sql = "UPDATE avaliacao SET valor = " + avaliacao.getValor() + ", "
					   + "id_usr = " + avaliacao.getId_usr() + " WHERE id = " + avaliacao.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
    /*
     * ### DELETE
     */
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM avaliacao WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}