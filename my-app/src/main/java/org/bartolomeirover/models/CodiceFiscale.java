package org.bartolomeirover.models;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CodiceFiscale {	
	
	public static String getSesso(String codice) {
		String s = codice.substring(9, 11); 
		
		if (Integer.valueOf(s) < 40) {
			return new String("M");
		} else {
			return new String("F");
		}
	}
	
	public static LocalDate getDataDiNascita(String codice) {
		String s = codice.substring(8, 11);

		String mese = s.charAt(0) + "";
		int giorno = Integer.valueOf(s.substring(1, 3));
		
		if (getSesso(codice).equals("F")) { 
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
		
		if (Integer.valueOf(codice.substring(6,8)) < 10) {
			return LocalDate.of(Integer.valueOf("20" + codice.substring(6,8)), m, giorno);
		} else {
			return LocalDate.of(Integer.valueOf("19" + codice.substring(6,8)), m, giorno);
		}
		
	}
	
	/**
	 * Returns true if <b>codice</b> is a correctly formatted CF, false otherwise.
	 * @param codice
	 * @return boolean
	 */
	public static boolean checkCF(String codice) {
		
		String rxx = "[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}";
		Pattern pt = Pattern.compile(rxx);
		Matcher mt = pt.matcher(codice);
		
		return mt.matches();
	}
	
}
