package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "voti")
public class Voti {
	
	/**
	 *  Fields
	 */
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(foreign = true)
	private VotazioneClassica votazione;
	
	@DatabaseField(foreign = true)
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
