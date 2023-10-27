package model;

public class Avaliacao  {

	private int id; // ID da avaliação em sí
	private int valor; // Valor dado na avaliação
    private int id_usr; // ID do úsuario que fez a avaliação.
	
    /*
     *  #################################### CONSTRUCTORS ####################################
     */

	public Avaliacao() {
		id = -1;
		valor = -1;
		id_usr = -1;
	}

	public Avaliacao(int id, int valor, int idusr) {
		this.id = id;
        this.valor = valor;
        this.id_usr = idusr;
	}

    /*
     *  #################################### GET / SETTERS ####################################
     */

    public int getId() {
        return id;
    }
    public int getId_usr() {
        return id_usr;
    }
    public int getValor() {
        return valor;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    /*
     *  #################################### METHODS ####################################
     */

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return (valor + " "  + " " + id_usr);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Avaliacao) obj).getId());
	}	
}