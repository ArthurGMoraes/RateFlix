package model;

public class Resp  {

	private int id; // ID da avaliação em sí
	private String valor; // Valor dado na avaliação
    private int id_disc; // ID do úsuario que fez a avaliação.
	
    /*
     *  #################################### CONSTRUCTORS ####################################
     */

	public Resp() {
		id = -1;
		valor = "";
		id_disc = -1;
	}

	public Resp(int id, String valor, int idisc) {
		this.id = id;
        this.valor = valor;
        this.id_disc = idisc;
	}

    /*
     *  #################################### GET / SETTERS ####################################
     */

    public int getId() {
        return id;
    }
    public int getId_disc() {
        return id_disc;
    }
    public String getValor() {
        return valor;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setId_usr(int id_disc) {
        this.id_disc = id_disc;
    }
    public void setValor(String valor) {
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
		return (valor + " "  + " " + id_disc);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Avaliacao) obj).getId());
	}	
}