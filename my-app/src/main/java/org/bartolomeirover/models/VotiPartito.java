package org.bartolomeirover.models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiPartito")
public class VotiPartito {
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private VotazioneClassica votazione;
	
	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private Partito partito;
	
	@DatabaseField
	private long totVoti;
	
	
	/**
	 *  Empty Constructor
	 */
	
	public VotiPartito() {
		
	}
	
	public VotiPartito(VotazioneClassica vc, Partito partito) {
		this.votazione = vc;
		this.partito = partito;
		totVoti = (long) 0;
	}
	
	/**
	 *  Other Methods
	 */
	
	public void aggiungiVoto() {
		totVoti += 1;
	}
	
}
