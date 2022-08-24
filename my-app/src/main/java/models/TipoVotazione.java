package models;

enum TipoVotazione {
	ORDINALE, CATEGORICO, CONPREFERENZA;
	
	@Override
	public String toString() {
		switch(this) {
			case ORDINALE: 
				return "ORDINALE";
			case CATEGORICO:
				return "CATEGORICO";
			case CONPREFERENZA:
				return "CATEGORICO CON PREFERENZA";
			default:
				throw new IllegalArgumentException();
		}
	}
}
