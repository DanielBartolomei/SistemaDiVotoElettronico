package models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "utente")
public class Utente{
	
	/**
	 *  FIelds and Getters
	 */
	
	@DatabaseField(unique = true)
	private CodiceFiscale cf;
	
	public CodiceFiscale getCF() {
		return cf;
	}
	
	@DatabaseField
	private String hashPassword;
	
	
	@DatabaseField(canBeNull = false)
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	@DatabaseField(canBeNull = false)
	private String cognome;
	
	public String getCognome() {
		return cognome;
	}
	
	@DatabaseField
	private boolean isAdmin;
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public Utente() {

	}
	
	public Utente(CodiceFiscale cf, String hashPassword, String nome, String cognome, boolean isAdmin) {
		Objects.requireNonNull(cf);
		Objects.requireNonNull(hashPassword);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		
		this.cf = cf;
		this.hashPassword = hashPassword;
		this.nome = nome;
		this.cognome = cognome;
		this.isAdmin = isAdmin;
	}
	
	/**
	 *  Other Methods
	 */
	
	
	
}
