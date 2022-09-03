package org.bartolomeirover.models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiCandidato")
public class VotiCandidato {
	
	/**
	 *  FIelds and Getters
	 */
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private VotazioneClassica votazione;
	
	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Candidato candidato;
	
	@DatabaseField
	private long totVoti;
	
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
}
