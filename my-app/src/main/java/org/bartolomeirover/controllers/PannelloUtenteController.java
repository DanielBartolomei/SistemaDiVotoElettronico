package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.bartolomeirover.common.Controller;
import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Referendum;
import org.bartolomeirover.models.Utente;
import org.bartolomeirover.models.VotazioneClassica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PannelloUtenteController extends Controller {

	@FXML
	private ListView<Object> votazioniAttiveList, votazioniEffettuateList;
	
	private String cf;
	
	public void sendData(String cf) {
		this.cf = cf;
		DbManager db = DbManager.getInstance();
		List<VotazioneClassica> votazioni = db.getAllVotazioni();
		List<Referendum> referendum = db.getAllReferendum();
		
		Utente utente = db.findUtenteByCF(this.cf);
		List<VotazioneClassica> votazioniUtente = db.getVotazioniUtente(utente);
		List<Referendum> referendumUtente = db.getReferendumUtente(utente);
		
		for (VotazioneClassica vc : votazioni) {
			if (votazioniUtente.contains(vc)) {
				votazioniEffettuateList.getItems().add(vc);
			} else {
				votazioniAttiveList.getItems().add(vc);
			}
		}
		for (Referendum r : referendum) {
			if (referendumUtente.contains(r)) {
				votazioniEffettuateList.getItems().add(r);
			} else {
				votazioniAttiveList.getItems().add(r);
			}
		}
		
		votazioniAttiveList.getItems().sort(null);
		votazioniEffettuateList.getItems().sort(null);
	}
	
	public void logout(ActionEvent event) {
		navigate("Home");
	}
	
	public void vota(ActionEvent event) {
		
	}
	
}
