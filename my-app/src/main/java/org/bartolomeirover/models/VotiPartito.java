package org.bartolomeirover.models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiPartito")
public class VotiPartito implements Comparable<VotiPartito>{
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private VotazioneClassica votazione;
	
	
	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private Partito partito;
	
	public Partito getPartito() {
		return partito;
	}
	
	@DatabaseField
	private long totVoti;
	
	public long getVoti() {
		return totVoti;
	}
	
	
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
	
	public void aggiungiVoto(int pos) {
		totVoti += pos;
	}

	@Override
	public int compareTo(VotiPartito o) {
		if( this.totVoti < o.totVoti ) return 1;
		if( this.totVoti > o.totVoti ) return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		return this.partito.getNome() + " : " + totVoti;
	}
	
}
