package models;

import java.util.GregorianCalendar;

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
	
	/**
	 *  Other Methods
	 */
	
	
	
	
}
