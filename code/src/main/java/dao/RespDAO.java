package dao;

import model.Resp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class RespDAO extends DAO {	
	public RespDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	/*
     * ### CREATE
     */

	public boolean insert(Resp resp) {
		boolean status = false;
		try {
			String sql = "INSERT INTO resp (conteudo, id_disc) "
		               + "VALUES ('"+ resp.getValor() + "', " + resp.getId_disc() + ");";
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
	
	public Resp get(int id) {
		Resp resp = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM resp WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 resp = new Resp(rs.getInt("id"), rs.getString("conteudo"),  rs.getInt("id_disc"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}
	
	public List<Resp> get() {
		return get("");
	}
	
	public List<Resp> getOrderByID() {
		return get("id");		
	}

    public List<Resp> getOrderByValor() {
		return get("valor");		
	}

    public List<Resp> getOrderByIDdisc() {
		return get("id_disc");		
	}
	
	private List<Resp> get(String orderBy) {
		List<Resp> resps = new ArrayList<Resp>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM resp" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	            Resp p = new Resp(rs.getInt("id"), rs.getString("conteudo"),  rs.getInt("id_disc"));
	            resps.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resps;
	}

    /*
     * ### UPDATE
     */

     public boolean update(Resp resp) {
		boolean status = false;
		try {  
			String sql = "UPDATE resp SET valor = " + resp.getValor() + ", "
					   + "id_disc = " + resp.getId_disc() + " WHERE id = " + resp.getId();
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
			st.executeUpdate("DELETE FROM resp WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}