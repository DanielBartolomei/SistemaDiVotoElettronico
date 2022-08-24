package models;

import java.util.GregorianCalendar;

public class CodiceFiscale {
	private String codice;
	private int annoDiNascita;
	private String nome;
	private String cognome;
	
	public CodiceFiscale(String codice, int anno, String nome, String cognome) {
		this.codice = codice;
		this.annoDiNascita = anno;
		this.cognome = cognome;
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public String getSesso() {
		String s = this.getSubstring(9, 11);
		
		if (Integer.valueOf(s) < 40) {
			return new String("M");
		} else {
			return new String("F");
		}
	}
	
	public GregorianCalendar getDataDiNascita() {
		String s = this.getSubstring(8, 11);

		String mese = s.charAt(0) + "";
		int giorno = Integer.valueOf(s.substring(1, 3));
		
		if (this.getSesso().equals("F")) {
			giorno -= 40;
		}
		int m;
		switch(mese) {
			case "A":
				m = 1;
				break;
			case "B":
				m = 2;
				break;
			case "C":
				m = 3;
				break;
			case "D":
				m = 4;
				break;
			case "E":
				m = 5;
				break;
			case "H":
				m = 6;
				break;
			case "L":
				m = 7;
				break;
			case "M":
				m = 8;
				break;
			case "P":
				m = 9;
				break;
			case "R":
				m = 10;
				break;
			case "S":
				m = 11;
				break;
			case "T":
				m = 12;
				break;
			default:
				m = 1;
		}
		return new GregorianCalendar(this.annoDiNascita, m, giorno);
	}
	
	private String getSubstring(int i, int f) {
		return codice.substring(i, f);
	}
}
