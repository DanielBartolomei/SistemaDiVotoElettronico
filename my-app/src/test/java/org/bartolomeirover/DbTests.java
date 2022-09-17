package org.bartolomeirover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.bartolomeirover.data.DbManager;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    	//TODO
    }

    @DisplayName("Test Registrazione")
    @Test
    void testRegistrazione() {
    	//TODO
    }
    
    @DisplayName("Test Voto Referendum")
    @Test
    void testVotoReferendum() {
    	//TODO
    }
    
    @DisplayName("Test Voto Votazione Classica")
    @Test
    void testVotoVotazione() {
    	//TODO
    }
    
    
    

}
