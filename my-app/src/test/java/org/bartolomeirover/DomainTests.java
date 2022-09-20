package org.bartolomeirover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.hash.Hashing;
import org.bartolomeirover.data.UserManager;
import org.bartolomeirover.models.Utente;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

public class DomainTests {
	
	
	@DisplayName("Test Lunghezza CF")
    @Test
	void testCodiceFiscale() {
		String pw = Hashing.sha256()
    			.hashString("siuii420", StandardCharsets.UTF_8).toString();
        Utente u = new Utente("BRTSMN98B15F205Z", pw, "Simone", "Rover");
        
        assertTrue(u.getCF().length() == 16);
	}
	
	@DisplayName("Test Validità Votante")
    @Test
	void testValidVoter() {
		String cfUtente = "BRTSMN98B15F205Z";
		
		assertTrue(UserManager.isValidVoter(cfUtente));

	}
}
