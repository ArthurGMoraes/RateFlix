package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class UserDAO extends DAO {	
	public UserDAO() 
	{
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Usuario user) {
		boolean status = false;

		
		try {
			String sql = "INSERT INTO usuario (nome, senha) "
		               + "VALUES ('"+ user.getNome() + "', " + user.getSenha() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Usuario get(int id) 
	{
		Usuario user = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next())
			{            
	        	user = new Usuario(rs.getInt("id"), rs.getString("nome"),  rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return user;
	}
	
	public Usuario getByNome(String n) {
		Usuario user = null;

	    try {
	        PreparedStatement ps = conexao.prepareStatement("SELECT * FROM usuario WHERE nome = ?");
	        ps.setString(1, n); // Bind the parameter safely

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            user = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("senha"));
	        }
	        //System.out.println(resp);
	        ps.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    return user;
	}
	
	
	public List<Usuario> get() 
	{
		return get("");
	}
	
	public List<Usuario> getOrderByNome() 
	{
		return get("nome USER");		
	}
	
	private List<Usuario> get(String nome) 
	{
		List<Usuario> users = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((nome.trim().length() == 0) ? "" : (" ORDER BY " + nome));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) 
			{	            	
	        	Usuario p = new Usuario(rs.getInt("id"), rs.getString("nome"),  rs.getString("senha"));
	            users.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return users;
	}
}