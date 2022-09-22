package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.bartolomeirover.common.Controller;
import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Referendum;
import org.bartolomeirover.models.VotazioneClassica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class PannelloAdminController extends Controller implements Initializable {
	
	@FXML
	private ListView<Object> votazioniAttiveList, votazioniConcluseList;
	
	public void logout(ActionEvent event) {
		navigate("Home");
	}
	
	public void goToCreaVotazione(ActionEvent event) {
		navigate("CreaVotazione");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		DbManager db = DbManager.getInstance();
		List<VotazioneClassica> votazioni = db.getAllVotazioni();
		List<Referendum> referendum = db.getAllReferendum();
		
		for (VotazioneClassica vc : votazioni) {
			if (vc.getFine().compareTo(DateUtils.fromLocalDateToString(LocalDate.now())) < 0) {
				votazioniConcluseList.getItems().add(vc);
			} else {
				votazioniAttiveList.getItems().add(vc);
			}
		}
		for (Referendum r : referendum) {
			if (r.getFine().compareTo(DateUtils.fromLocalDateToString(LocalDate.now())) < 0) {
				votazioniConcluseList.getItems().add(r);
			} else {
				votazioniAttiveList.getItems().add(r);
			}
		}
		
		votazioniAttiveList.getItems().sort(null);
		votazioniConcluseList.getItems().sort(null);
		
	}
}
