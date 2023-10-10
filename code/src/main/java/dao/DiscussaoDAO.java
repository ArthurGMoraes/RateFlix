package dao;

import model.Discussao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class DiscussaoDAO extends DAO {	
	public DiscussaoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Discussao discussao) {
		boolean status = false;
		try {
			String sql = "INSERT INTO discussao (titulo, conteudo, autor, curtidas, data) "
		               + "VALUES ('"+ discussao.getTitulo() + "', '"
		               + discussao.getConteudo() + "', '" + discussao.getAutor() + "', '" + discussao.getCurtidas() + "', '" + discussao.getData() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Discussao get(int id) {
		Discussao discussao = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM discussao WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 discussao = new Discussao(rs.getInt("id"), rs.getString("titulo"),  rs.getString("conteudo"),  
		        			rs.getString("autor"), rs.getInt("curtidas"), rs.getString("data"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return discussao;
	}
	
	public List<Discussao> get() {
		return get("");
	}
	
	public List<Discussao> getOrderByID() {
		return get("id");		
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
	}
}