package models;

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
	
	/**
	 *  Other Methods
	 */
	
	
	
}
