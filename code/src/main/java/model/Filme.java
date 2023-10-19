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
		tipo = "nao definido";
		avaliacao = -1;
	}

	public Filme(int id, String tipo, int avaliacao) {
		setId(id);
		setAvaliacao(avaliacao);
		setTipo(tipo);
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

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
			this.tipo = tipo;
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