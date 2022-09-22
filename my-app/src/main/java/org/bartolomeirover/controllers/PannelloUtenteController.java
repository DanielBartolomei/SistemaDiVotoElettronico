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
		System.out.println("Votazioni tot: "+votazioni.toString());
		System.out.println("Referendum tot: "+referendum.toString());
		
		Utente utente = db.findUtenteByCF(this.cf.toUpperCase());
		System.out.println("Utente dal db: " + utente.toString());
		List<VotazioneClassica> votazioniUtente = db.getVotazioniUtente(utente);
		List<Referendum> referendumUtente = db.getReferendumUtente(utente);
		System.out.println("Votazioni utente: "+votazioniUtente.toString());
		System.out.println("Referendum utente: "+referendumUtente.toString());
		
		for (VotazioneClassica vc : votazioni) {
			System.out.println("Evaluating votazione: " + vc.toString());
			if (votazioniUtente.contains(vc)) {
				votazioniEffettuateList.getItems().add(vc);
			} else {
				votazioniAttiveList.getItems().add(vc);
			}
		}
		for (Referendum r : referendum) {
			System.out.println("Evaluating referendum: " + r.toString());
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
