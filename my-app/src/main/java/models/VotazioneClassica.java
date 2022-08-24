package models;

import java.util.GregorianCalendar;

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
	
	/**
	 *  Other Methods
	 */
	
	
	
	
}
