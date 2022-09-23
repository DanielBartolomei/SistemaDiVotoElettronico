package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.bartolomeirover.App;
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
	
	private Utente utente;
	
	public void sendData(Utente u) {
		this.utente = u;
		DbManager db = DbManager.getInstance();
		List<VotazioneClassica> votazioni = db.getAllVotazioni();
		List<Referendum> referendum = db.getAllReferendum();
		List<VotazioneClassica> votazioniUtente = db.getVotazioniUtente(utente);
		List<Referendum> referendumUtente = db.getReferendumUtente(utente);
		System.out.println(votazioniUtente);
		
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
		if (votazioniAttiveList.getSelectionModel().getSelectedItem() == null) return;
		
		if (votazioniAttiveList.getSelectionModel().getSelectedItem() instanceof Referendum) {
			Referendum r = (Referendum) votazioniAttiveList.getSelectionModel().getSelectedItem();
			navigate("VotazioneReferendum");
			VotazioneReferendumController contrRef = (VotazioneReferendumController) App.getController();
			contrRef.sendData(r, utente);
		} else {
			VotazioneClassica vc = (VotazioneClassica) votazioniAttiveList.getSelectionModel().getSelectedItem();
			switch(vc.getTipo()) {
				case ORDINALE_PARTITI:
				case ORDINALE_CANDIDATI:
					navigate("VotazioneOrdinale");
					VotazioneOrdinaleController contrOrd = (VotazioneOrdinaleController) App.getController();
					contrOrd.sendData(vc, utente);
					break;
				case CATEGORICO_PARTITI:
				case CATEGORICO_CANDIDATI:
				case CON_PREFERENZA:
					navigate("VotazioneCategorica");
					VotazioneCategoricaController contrCat = (VotazioneCategoricaController) App.getController();
					contrCat.sendData(vc, utente);
					break;
				default:
					System.out.println("Unexpected TipoVotazione");
			}
		}
	}
	
}
