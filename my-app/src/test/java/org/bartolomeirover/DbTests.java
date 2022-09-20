package org.bartolomeirover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.TipoVotazione;
import org.bartolomeirover.models.Utente;

import com.google.common.hash.Hashing;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;

public class DbTests {

    private static DbManager db = DbManager.getInstance();

    @BeforeAll
    public static void setup() {
        File file = new File("./data.db");
        file.delete();

        db.checkCreation();
        db.createFakeData();
    }

    @DisplayName("Test Autenticazione")
    @Test
    void testLogin() {
    	String pw = Hashing.sha256()
    			.hashString("siuii420", StandardCharsets.UTF_8).toString();
        Utente u = db.autentica("BRTDNL98E27F205P", pw);
        
        assertNotNull(u);
    }

    @DisplayName("Test Registrazione")
    @Test
    void testRegistrazione() {
    	    	
    	String hpwd = Hashing.sha256()
    			.hashString("BestPassword12345", StandardCharsets.UTF_8).toString();
    	
    	Utente u = db.registra("RVRDNL98F25F205N", "Daniel", "Rover", hpwd);
    	
    	assertNotNull(u);
    }
    
    @DisplayName("Test New Referendum")
    @Test
    void testVotoReferendum() {
    	LocalDate inizio = LocalDate.of(2023, 02, 10);
    	LocalDate fine = LocalDate.of(2023, 02, 15);
        assertTrue(db.aggiungiReferendum("Risparmio Energetico", inizio, fine, true));
    }
    
    @DisplayName("Test New Votazione Classica")
    @Test
    void testVotoVotazione() {
    	LocalDate inizio = LocalDate.of(2023, 02, 10);
    	LocalDate fine = LocalDate.of(2023, 02, 15);
    	assertTrue(db.aggiungiVotazione("Votazione Comunale", inizio, fine, false, TipoVotazione.CATEGORICO));
    }
    
    
    

}
