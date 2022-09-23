package org.bartolomeirover.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "voti")
public class Voti {
	
	//@ invariant votazione!=null && utente!=null;
	
	/**
	 *  Fields
	 */
	
	@DatabaseField(generatedId = true)
	private int id;
	
	public int getId() {
		return id;
	}
	
	@DatabaseField(foreign = true, uniqueCombo = true)
	private VotazioneClassica votazione;
	
	public VotazioneClassica getVotazione() {
		return this.votazione;
	}
	
	@DatabaseField(foreign = true, uniqueCombo = true)
	private Utente utente;
	
	/**
	 * Empty Constructor
	 */
	
	public Voti() {
		
	}
	
	public Voti(VotazioneClassica vc, Utente utente) {
		this.votazione = vc;
		this.utente = utente;
	}

	
	
	/**
	 *  Other Methods
	 */

}
