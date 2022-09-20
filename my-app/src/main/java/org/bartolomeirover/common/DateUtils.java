package org.bartolomeirover.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	/*LocalDate ld = LocalDate.of(2022, 9, 25); //LocalDate.now()
	
	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	String fs = ld.format(formatter);
    System.out.println(fs);*/
	
	private final static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	
	public DateUtils() {}
	
	public static String fromLocalDateToString(LocalDate ld) {
		String fs = ld.format(formatter);
		return fs;
	}
	
	public static LocalDate fromStringToLocalDate(String s) {
		return LocalDate.parse(s, formatter);
	}
	
	public static LocalDate now() {
		return LocalDate.now();
	}
	
	
	
}
