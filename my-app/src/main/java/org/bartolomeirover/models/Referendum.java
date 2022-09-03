package org.bartolomeirover.models;

import java.util.GregorianCalendar;
import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "referendum")
public class Referendum{
	
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
	
	public Referendum(int id, String nome, GregorianCalendar dataInizio, 
			GregorianCalendar dataFine, boolean hasQuorum) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(dataInizio);
		Objects.requireNonNull(dataFine);
		
		this.id = id;
		this.nome = nome;
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
}