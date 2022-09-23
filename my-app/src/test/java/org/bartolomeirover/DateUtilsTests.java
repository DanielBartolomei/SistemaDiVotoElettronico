package org.bartolomeirover;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.bartolomeirover.common.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateUtilsTests {

	@DisplayName("Test da String a LocalDate")
    @Test
    void testFromStringToLocalDate() {
		String s = LocalDate.now().toString();
		LocalDate ld = LocalDate.now();
		
		assertTrue(ld.equals(DateUtils.fromStringToLocalDate(s)));
		
	}
	
	@DisplayName("Test da LocalDate a String")
    @Test
    void testFromLocalDateToString() {
		String s = LocalDate.now().toString();
		LocalDate ld = LocalDate.now();
		
		assertTrue(s.equals(DateUtils.fromLocalDateToString(ld)));
		
	}
	
}
