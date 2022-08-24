package models;

import java.util.GregorianCalendar;

public abstract class Votazione implements Comparable<Votazione>{

	private int id_votazione;
	private GregorianCalendar dataInizio;
	private GregorianCalendar dataFine;
	private long totVoti;
	private long totValidi;
	
	
	public Votazione(GregorianCalendar dataInizio, GregorianCalendar dataFine) {
		// id_votazione = ...
		totVoti = 0;
		totValidi = 0;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	
	public Votazione(int id_votazione, GregorianCalendar dataInizio, GregorianCalendar dataFine,
			long totVoti, long totValidi) {
		this.id_votazione = id_votazione;
		this.totVoti = totVoti;
		this.totValidi = totValidi;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	
	
	
	public int getId() {
		return id_votazione;
	}
	
	public GregorianCalendar getDataInizio() {
		return dataInizio;
	}
	
	public GregorianCalendar getDataFine() {
		return dataFine;
	}
	
	public long getVotantiTotali() {
		return totVoti;
	}
	
	public long getTotaliValidi() {
		return totValidi;
	}
	
	@Override
	public int compareTo(Votazione v) {
		return this.dataInizio.compareTo(v.dataInizio);
	}
		
}
