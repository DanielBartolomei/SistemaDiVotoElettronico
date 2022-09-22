package org.bartolomeirover.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "candidato")
public class Candidato{
	
	/**
	 *  FIelds and Getters
	 */
	
	@DatabaseField(generatedId = true)
	private int id;
	
	public int getId() {
		return id;
	}
	
	@DatabaseField(canBeNull = false)
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	@DatabaseField(canBeNull = false)
	private String cognome;
	
	public String getCognome() {
		return cognome;
	}
	
	@DatabaseField(foreign = true, canBeNull = false)
	private Partito partito;
	
	public void setPartito(Partito partito) {
		this.partito = partito;
	}
	
	public Partito getPartito() {
		return partito;
	}
	
	/**
	 *  Empty Constructor
	 */
	
	public Candidato() {
		
	}
	
	public Candidato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.partito = null;
	}
	
	public Candidato(String nome, String cognome, Partito partito) {
		this.nome = nome;
		this.cognome = cognome;
		this.partito = partito;
	}
	
	/**
	 *  Other Methods
	 */
	
	@Override
	public String toString() {
		return this.cognome + " " + this.nome + " (" + this.partito.toString() + ")";
	}
	
}
