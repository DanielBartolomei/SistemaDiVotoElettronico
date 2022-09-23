package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.data.VotesManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;
import org.bartolomeirover.models.Referendum;
import org.bartolomeirover.models.VotazioneClassica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class PannelloAdminController extends Controller implements Initializable {
	
	@FXML
	private ListView<Object> votazioniAttiveList, votazioniConcluseList;
	
	public void logout(ActionEvent event) {
		navigate("Home");
	}
	
	public void goToCreaVotazione(ActionEvent event) {
		navigate("CreaVotazione");
	}
	
	public void partiti(ActionEvent event) {
		navigate("GestionePartiti");
	}
	
	public void delete(ActionEvent event) {
		if (votazioniAttiveList.getSelectionModel().getSelectedItem() == null) return;
		
		DbManager db = DbManager.getInstance();
		
		if (votazioniAttiveList.getSelectionModel().getSelectedItem() instanceof Referendum) {
			Referendum r = (Referendum) votazioniAttiveList.getSelectionModel().getSelectedItem();
			db.rimuoviReferendum(r);
		} else {
			VotazioneClassica vc = (VotazioneClassica) votazioniAttiveList.getSelectionModel().getSelectedItem();
			db.rimuoviVotazione(vc);
		}
		
		votazioniAttiveList.getItems().remove(votazioniAttiveList.getSelectionModel().getSelectedItem());
	}
	
	public void visualizza(ActionEvent event) {
		if (votazioniConcluseList.getSelectionModel().getSelectedItem() == null) return;
		
		
		if (votazioniConcluseList.getSelectionModel().getSelectedItem() instanceof Referendum) {
			Referendum r = (Referendum) votazioniConcluseList.getSelectionModel().getSelectedItem();
			String esito = VotesManager.getEsitoReferendum(r);
			
			Alert esitoAlert = new Alert(AlertType.INFORMATION);
			esitoAlert.setTitle("Esito referendum " + r.getNome());
			esitoAlert.setHeaderText(r.getQuesito());
			esitoAlert.setContentText("Esito: " + esito);
			esitoAlert.showAndWait();
		} else {
			VotazioneClassica vc = (VotazioneClassica) votazioniConcluseList.getSelectionModel().getSelectedItem();
			DbManager db = DbManager.getInstance();
			
			switch(vc.getTipo()) {
				case CATEGORICO_PARTITI:
				case ORDINALE_PARTITI:
				case CON_PREFERENZA:
					Map<Partito, Integer> esitiPartito = db.getPartitoVincitore(vc);
					if (esitiPartito.size() > 1) {
						// pareggio
						Partito p = null;
						Integer numVoti = null;
						Iterator<Partito> iterator = esitiPartito.keySet().iterator();
						int i = 0;
						String vincitoriPrompt = "";
						while(iterator.hasNext()) {
							p = iterator.next();
							if (i == 0) {
								vincitoriPrompt = vincitoriPrompt + p.toString();
							} else {
								vincitoriPrompt = vincitoriPrompt + ", " + p.toString();
							}
							numVoti = esitiPartito.get(p);
							i++;
						}
						Alert esitoAlert = new Alert(AlertType.INFORMATION);
						esitoAlert.setTitle("Esito votazione " + vc.getNome());
						esitoAlert.setHeaderText("Pareggio");
						esitoAlert.setContentText(vincitoriPrompt + " con " + numVoti + " voti.");
						esitoAlert.showAndWait();
						
					} else {
						// un solo vincitore o nessuno
						Iterator<Partito> iterator = esitiPartito.keySet().iterator();
						Partito vincitore = null;
						Integer numVoti = null;
						if (iterator.hasNext()) {
							vincitore = iterator.next();
							numVoti = esitiPartito.get(vincitore);
						}
						if (vincitore == null || numVoti == null) {
							Alert errorAlert = new Alert(AlertType.ERROR);
							errorAlert.setTitle("Errore nella lettura esito");
							errorAlert.setHeaderText("Non è stato possibile ottenere il vincitore della votazione.");
							errorAlert.setContentText("Errore: Mappa vuota");
							errorAlert.showAndWait();
						} else {
							Alert esitoAlert = new Alert(AlertType.INFORMATION);
							esitoAlert.setTitle("Esito votazione " + vc.getNome());
							esitoAlert.setHeaderText("Vincitore unico");
							esitoAlert.setContentText(vincitore.toString() + " con " + numVoti + " voti.");
							esitoAlert.showAndWait();
						}
						
					}
					break;
				case CATEGORICO_CANDIDATI:
				case ORDINALE_CANDIDATI:
					Map<Candidato, Integer> esiti = db.getCandidatoVincitore(vc);
					if (esiti.size() > 1) {
						// pareggio
						Candidato c = null;
						Integer numVoti = null;
						Iterator<Candidato> iterator = esiti.keySet().iterator();
						int i = 0;
						String vincitoriPrompt = "";
						while(iterator.hasNext()) {
							c = iterator.next();
							if (i == 0) {
								vincitoriPrompt = vincitoriPrompt + c.toString();
							} else {
								vincitoriPrompt = vincitoriPrompt + ", " + c.toString();
							}
							numVoti = esiti.get(c);
							i++;
						}
						Alert esitoAlert = new Alert(AlertType.INFORMATION);
						esitoAlert.setTitle("Esito votazione " + vc.getNome());
						esitoAlert.setHeaderText("Pareggio");
						esitoAlert.setContentText(vincitoriPrompt + " con " + numVoti + " voti.");
						esitoAlert.showAndWait();
						
					} else {
						// un solo vincitore o nessuno
						Iterator<Candidato> iterator = esiti.keySet().iterator();
						Candidato vincitore = null;
						Integer numVoti = null;
						if (iterator.hasNext()) {
							vincitore = iterator.next();
							numVoti = esiti.get(vincitore);
						}
						if (vincitore == null || numVoti == null) {
							Alert errorAlert = new Alert(AlertType.ERROR);
							errorAlert.setTitle("Errore nella lettura esito");
							errorAlert.setHeaderText("Non è stato possibile ottenere il vincitore della votazione.");
							errorAlert.setContentText("Errore: Mappa vuota");
							errorAlert.showAndWait();
						} else {
							Alert esitoAlert = new Alert(AlertType.INFORMATION);
							esitoAlert.setTitle("Esito votazione " + vc.getNome());
							esitoAlert.setHeaderText("Vincitore unico");
							esitoAlert.setContentText(vincitore.toString() + " con " + numVoti + " voti.");
							esitoAlert.showAndWait();
						}
						
					}
					break;
				default:
					
			}
		}
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
