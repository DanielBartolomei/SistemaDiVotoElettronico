package models;

public class Utente extends Persona {
	private CodiceFiscale cf;
	private boolean isAdmin;
	
	public Utente(String nome, String cognome, CodiceFiscale cf, boolean isAdmin) {
		super(nome, cognome);
		this.cf = cf;
		this.isAdmin = isAdmin;
	}
	
	public CodiceFiscale getCF() {
		return this.cf;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
}
