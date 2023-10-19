package dao;

import model.Discussao;
import model.Filme;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class FilmeDAO extends DAO {	
	public FilmeDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Filme filme) {
		boolean status = false;
		try {
			String sql = "INSERT INTO filme (id, tipo, avaliacao) "
	                + "VALUES (?, ?, ?)";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        st.setInt(1, filme.getId());            
	        st.setString(2, filme.getTipo());
	        st.setInt(3, filme.getAvaliacao());   

	        st.executeUpdate();
	        st.close();
	        status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Filme get(int id) {
		Filme filme = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM filme WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 filme = new Filme(rs.getInt("id"),
		        			rs.getString("tipo"), rs.getInt("avaliacao"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return filme;
	}
	
	/*public List<Filme> get() {
		return get("");
	}
	
	public List<Discussao> getOrderByID() {
		return get("id DESC");		
	}
	
	private List<Discussao> get(String orderBy) {
		List<Discussao> discussoes = new ArrayList<Discussao>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM discussao" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Discussao p = new Discussao(rs.getInt("id"), rs.getString("titulo"),  rs.getString("conteudo"),  
	        			rs.getString("autor"), rs.getInt("curtidas"), rs.getString("data"));
	            discussoes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return discussoes;
	}*/
} 