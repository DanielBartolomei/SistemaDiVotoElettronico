package models;

import java.util.GregorianCalendar;

public class Referendum extends Votazione{
	
	private boolean hasQuorum;
	private long totFavorevoli;
	private long totContrari;
	
	public Referendum(GregorianCalendar dataInizio, GregorianCalendar dataFine, boolean hasQuorum) {
		super(dataInizio, dataFine);
		totFavorevoli = 0;
		totContrari = 0;
	}
	
	public Referendum(int id_votazione, GregorianCalendar dataInizio, GregorianCalendar dataFine, boolean hasQuorum,
			long totFavorevoli, long totContrari, long totVoti) {
		super(id_votazione, dataInizio, dataFine, totVoti, totFavorevoli+totContrari);
		this.totFavorevoli = totFavorevoli;
		this.totContrari = totContrari;
	}
	
	
	
}
