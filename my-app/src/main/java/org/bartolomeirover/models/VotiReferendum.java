package org.bartolomeirover.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiReferendum")
public class VotiReferendum {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Referendum referendum;
	
	public void setUtente(Referendum referendum) {
		this.referendum = referendum;
	}
	
	@DatabaseField(foreign = true, foreignColumnName = "cf",  uniqueCombo = true)
	private Utente utente;
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public Utente getUtente() {
		return utente;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public VotiReferendum() {
		
	}
	
	public VotiReferendum(Referendum referendum, Utente utente) {
		this.referendum = referendum;
		this.utente = utente;
	}
	
	/**
	 *  Other Methods
	 */
	
}