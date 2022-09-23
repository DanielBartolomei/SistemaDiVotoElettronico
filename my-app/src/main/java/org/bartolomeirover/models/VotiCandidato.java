package org.bartolomeirover.models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiCandidato")
public class VotiCandidato implements Comparable<VotiCandidato>{
	
	/**
	 *  FIelds and Getters
	 */
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private VotazioneClassica votazione;
	
	@DatabaseField(foreign = true, uniqueCombo = true, canBeNull = false)
	private Candidato candidato;
	
	public Candidato getCandidato() {
		return this.candidato;
	}
	
	@DatabaseField
	private long totVoti;
	
	public long getVoti() {
		return totVoti;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public VotiCandidato() {
		
	}
	
	public VotiCandidato(VotazioneClassica vc, Candidato candidato) {
		this.votazione = vc;
		this.candidato = candidato;
		totVoti = 0;
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
	public int compareTo(VotiCandidato o) {
		if( this.totVoti < o.totVoti ) return 1;
		if( this.totVoti > o.totVoti ) return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		return getCandidato().toString() + ":" + totVoti;
	}
}
