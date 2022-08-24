package models;

import java.util.GregorianCalendar;

public class VotazioneClassica extends Votazione {

	private boolean isAssoluta;
	private TipoVotazione tipoVotazione;
	
	
	public VotazioneClassica(GregorianCalendar dataInizio, GregorianCalendar dataFine, boolean isAssoluta, 
			TipoVotazione tipoVotazione) {
		super(dataInizio, dataFine);
		this.isAssoluta = isAssoluta;
		this.tipoVotazione = tipoVotazione;
	}
	
	public VotazioneClassica(int id_votazione, GregorianCalendar dataInizio, GregorianCalendar dataFine, boolean isAssoluta, 
			TipoVotazione tipoVotazione, long totVoti, long totValidi) {
		super(id_votazione, dataInizio, dataFine, totVoti, totValidi);
		this.tipoVotazione = tipoVotazione;
		this.isAssoluta = isAssoluta;
	}
	
	public TipoVotazione getTipo() {
		return tipoVotazione;
	}
	
	public boolean isAssoluta() {
		return isAssoluta;
	}
}
