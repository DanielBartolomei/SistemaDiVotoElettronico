package org.bartolomeirover.models;

import java.util.GregorianCalendar;
import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votazione")
public class VotazioneClassica  {
	
	/**
	 *  Fields and Getters
	 */
	
	@DatabaseField(generatedId = true)
	private int id;
	
	public int getId() {
		return id;
	}
	
	@DatabaseField
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	@DatabaseField(canBeNull = false)
	private GregorianCalendar dataInizio;
	
	public GregorianCalendar getInizio() {
		return dataInizio;
	}
	
	@DatabaseField(canBeNull = false)
	private GregorianCalendar dataFine;
	
	public GregorianCalendar getFine() {
		return dataFine;
	}
	
	@DatabaseField
	private boolean isAssoluta;
	
	public boolean isAssoluta() {
		return isAssoluta;
	}
	
	@DatabaseField
	private TipoVotazione tipoVotazione;
	
	public TipoVotazione getTipo() {
		return tipoVotazione;
	}
	
	@DatabaseField
	private long totValidi;
	
	public long getVotiValidi() {
		return totValidi;
	}
	
	@DatabaseField
	private long totVoti;
	
	public long getVotiTotali() {
		return totVoti;
	}
	
	
	/**
	 * Empty Constructor
	 */
	
	public VotazioneClassica() {
	
	}
	
	public VotazioneClassica(int id, String nome,  GregorianCalendar dataInizio, GregorianCalendar dataFine, 
			boolean isAssoluta, TipoVotazione tipoVotazione) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(dataInizio);
		Objects.requireNonNull(dataFine);
		
		this.id = id;
		this.nome = nome;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.isAssoluta = isAssoluta;
		this.tipoVotazione = tipoVotazione;
		totValidi = 0;
		totVoti = 0;
	}
	
	/**
	 *  Other Methods
	 */
	
	public void addVote() {
		totValidi += 1;
		totVoti += 1;
	}
	
	public void addBianca() {
		totVoti +=1;
	}
	
	
	
	
}
