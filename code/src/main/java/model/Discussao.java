package model;

import java.io.Serializable;

public class Discussao implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String titulo;
	private String conteudo;
	private String autor;
	private int curtidas;
	private String data;
	
	public Discussao() {
		id = -1;
		titulo = "nao definido";
		conteudo = "nao definido";
		autor = "nao definido";
		curtidas = -1;
		data = "nao definido";
	}

	public Discussao(int id, String titulo, String conteudo, String autor, int curtidas, String data) {
		setId(id);
		setTitulo(titulo);
		setConteudo(conteudo);
		setAutor(autor);
		setCurtidas(curtidas);
		setData(data);
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (titulo.length() >= 2)
			this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		if (conteudo.length() >= 2)
			this.conteudo = conteudo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		if (autor.length() >= 1)
			this.autor = autor;
	}

	public int getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(int curtidas){
		this.curtidas = curtidas;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	



	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return (titulo + " " + conteudo + " " + autor + " " + curtidas + " " + data);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Discussao) obj).getId());
	}	
}