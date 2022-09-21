package org.bartolomeirover.controllers;

import java.time.LocalDate;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CreaReferendumController extends Controller {

	private String nomeVotazione;
	private LocalDate dataInizio, dataFine;
	private boolean hasQuorum;
	
	@FXML
	private TextArea domandaText;
	
	public void sendData(String nome, LocalDate inizio, LocalDate fine, boolean hasQuorum) {
		this.nomeVotazione = nome;
		this.dataInizio = inizio;
		this.dataFine = fine;
		this.hasQuorum = hasQuorum;
	}
	
	public void submit(ActionEvent event) {
		if (domandaText.getText() == null || domandaText.getText().equals("")) return;
		
		DbManager db = DbManager.getInstance();
		boolean esito = db.aggiungiReferendum(nomeVotazione, domandaText.getText(), dataInizio, dataFine, hasQuorum);
		System.out.println("Esito creazione referendum: " + esito);
		navigate("PannelloAdmin");
	}
	
	public void back(ActionEvent event) {
		navigate("PannelloAdmin");
	}
	
}
