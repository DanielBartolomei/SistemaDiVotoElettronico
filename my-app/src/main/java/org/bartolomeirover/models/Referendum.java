package org.bartolomeirover.models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "referendum")
public class Referendum implements Comparable<Object>{
	
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
	
	@DatabaseField
	private String quesito;
	
	public String getQuesito() {
		return quesito;
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
	private boolean hasQuorum;
	
	public boolean hasQuorum() {
		return hasQuorum;
	}
	
	@DatabaseField
	private long totFavorevoli;
	
	public long getFavorevoli() {
		return totFavorevoli;
	}
	
	@DatabaseField
	private long totContrari;
	
	public long getContrari() {
		return totContrari;
	}
	
	@DatabaseField
	private long totVoti;
	
	public long getVotiTotali() {
		return totVoti;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public Referendum() {
		
	}
	
	public Referendum(String nome, String quesito, String dataInizio, 
			String dataFine, boolean hasQuorum) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(quesito);
		Objects.requireNonNull(dataInizio);
		Objects.requireNonNull(dataFine);
		
		this.nome = nome;
		this.quesito = quesito;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.hasQuorum = hasQuorum;
		totFavorevoli = 0;
		totContrari = 0;
		totVoti = 0;
	}
	
	/**
	 *  Other Methods
	 */
	
	public void aggiungiVoto(TipoEsitoRef esito) {
		switch(esito) {
			case FAVOREVOLE:
				totFavorevoli += 1;
				break;
		
			case CONTRARIO:
				totContrari += 1;
				break;
			default:
				break;
		}
		totVoti +=1;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Referendum) {
			Referendum r = (Referendum)o;
			return this.dataInizio.compareTo(r.dataInizio);
		} else {
			VotazioneClassica vc = (VotazioneClassica)o;
			return this.dataInizio.compareTo(vc.getInizio());
		}
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
	
	@Override
	public boolean equals(Object o) {
		Objects.requireNonNull(o);
		
		if(!(o instanceof Referendum)) return false;
		
		Referendum other = (Referendum) o;
		return other.id == this.id;
		
	}
}