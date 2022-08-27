package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiReferendum")
public class VotiReferendum {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Referendum referendum;
	
	@DatabaseField(foreign = true, foreignColumnName = "cf",  uniqueCombo = true)
	private Utente utente;
	
	public Utente getUtente() {
		return utente;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public VotiReferendum() {
		
	}
	
	/**
	 *  Other Methods
	 */
	
}
