package org.bartolomeirover.models;

public enum TipoVotazione {
	ORDINALE_PARTITI, ORDINALE_CANDIDATI, CATEGORICO_PARTITI, CATEGORICO_CANDIDATI, CONPREFERENZA;
	
	@Override
	public String toString() {
		switch(this) {
			case ORDINALE_PARTITI: 
				return "ORDINALE (PARTITI)";
			case ORDINALE_CANDIDATI:
				return "ORDINALE (CANDIDATI)";
			case CATEGORICO_PARTITI:
				return "CATEGORICO (PARTITI)";
			case CATEGORICO_CANDIDATI:
				return "CATEGORICO (CANDIDATI)";
			case CONPREFERENZA:
				return "CATEGORICO (PARTITI) CON PREFERENZA";
			default:
				throw new IllegalArgumentException();
		}
	}
}
