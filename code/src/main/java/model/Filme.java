package model;



public class Filme  {
	private int id;
	private String titulo;
	private String descricao;
	private String tipo;
	private String poster;
	private String data;
	private int avaliacao;
	
	public Filme() {
		id = -1;
		titulo = "nao definido";
		descricao = "nao definido";
		tipo = "nao definido";
		data = "nao definido";
		avaliacao = -1;
	}

	public Filme(int id, String titulo, int avaliacao, String data) {
		setId(id);
		setTitulo(titulo);
		setAvaliacao(avaliacao);
	
		
		setData(data);
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (titulo.length() >= 2) {
			this.titulo = titulo;}
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
		return (titulo + " "  + " " + data);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Discussao) obj).getId());
	}	
}