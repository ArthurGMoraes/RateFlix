package model;

public class Avaliacao  {

	private int id; // ID da avaliação em sí
	private int valor; // Valor dado na avaliação
    private int id_usr; // ID do úsuario que fez a avaliação.
    private int id_filme;
	
    /*
     *  #################################### CONSTRUCTORS ####################################
     */

	public Avaliacao() {
		id = -1;
		valor = -1;
		id_usr = -1;
		id_filme = -1;
	}

	public Avaliacao(int id, int valor, int idusr, int idfilme) {
		this.id = id;
        this.valor = valor;
        this.id_usr = idusr;
        this.id_filme = idfilme;
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
    public int getId_filme() {
        return id_filme;
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
    public void setId_filme(int id_filme) {
        this.id_filme = id_filme;
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