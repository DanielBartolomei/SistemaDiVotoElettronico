package org.bartolomeirover.controllers;

import java.util.List;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;
import org.bartolomeirover.models.Utente;
import org.bartolomeirover.models.VotazioneClassica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class VotazionePreferenzaController extends Controller {
	
	@FXML
	private ComboBox<Candidato> sceltaCombo;
	
	private VotazioneClassica votazione;
	private Utente utente;
	private Partito partito;
	
	public void sendData(VotazioneClassica vc, Utente u, Partito p) {
		this.votazione = vc;
		this.utente = u;
		this.partito = p;
		
		DbManager db = DbManager.getInstance();
		List<Candidato> candidati = db.getListaCandidati(partito);
		sceltaCombo.getItems().addAll(candidati);
	}
	
	public void blank(ActionEvent event) {
		DbManager db = DbManager.getInstance();
		db.registraVotoVotazione(utente, votazione);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(utente);
	}
	
	public void confirm(ActionEvent event) {
		if (sceltaCombo.getValue() == null) return;
		
		DbManager db = DbManager.getInstance();
		db.registraEsitoPreferenza(votazione, sceltaCombo.getValue());
		db.registraVotoVotazione(utente, votazione);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(utente);
	}

}
