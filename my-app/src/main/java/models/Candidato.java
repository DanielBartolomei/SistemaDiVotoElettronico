package models;

public class Candidato extends Persona {
	private int id;
	private Partito partito;
	
	public Candidato(String nome, String cognome, int id, Partito partito) {
		super(nome, cognome);
		this.id = id;
		this.partito = partito;
	}
	
	public int getID() {
		return this.id;
	}
	
	public Partito getPartito() {
		return this.partito;
	}
}
