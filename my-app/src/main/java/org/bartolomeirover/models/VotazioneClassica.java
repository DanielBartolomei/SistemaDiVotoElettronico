package org.bartolomeirover.models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votazione")
public class VotazioneClassica implements Comparable<Object> {
	
	/*@
	  @ invariant nome!=null && dataInizio!=null && dataFine!=null && totVoti >= totValidi;
	  @
	  @ 
	  @*/
	
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
	private String dataInizio;
	
	public String getInizio() {
		return dataInizio;
	}
	
	@DatabaseField(canBeNull = false)
	private String dataFine;
	
	public String getFine() {
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
	
	public VotazioneClassica(String nome,  String dataInizio, String dataFine, 
			boolean isAssoluta, TipoVotazione tipoVotazione) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(dataInizio);
		Objects.requireNonNull(dataFine);
		
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
	//@ ensures totValidi=\old(totValidi+1) && totVoti=\old(totVoti+1);
	public void aggiungiVoto() {
		totValidi += 1;
		totVoti += 1;
	}
	
	//@ ensures totVoti=\old(totVoti+1);
	public void aggiungiBianca() {
		totVoti +=1;
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Referendum) {
			Referendum r = (Referendum)o;
			return this.dataInizio.compareTo(r.getInizio());
		} else {
			VotazioneClassica vc = (VotazioneClassica)o;
			return this.dataInizio.compareTo(vc.dataInizio);
		}
		
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		
		if(!(o instanceof VotazioneClassica)) return false;
		
		VotazioneClassica other = (VotazioneClassica) o;
		return other.id == this.id;
		
	}
	
	
}
