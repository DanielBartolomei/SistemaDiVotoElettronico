package org.bartolomeirover.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.data.UserManager;
import org.bartolomeirover.data.VotesManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;
import org.bartolomeirover.models.Referendum;
import org.bartolomeirover.models.Utente;
import org.bartolomeirover.models.VotazioneClassica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class PannelloUtenteController extends Controller {

	@FXML
	private ListView<Object> votazioniAttiveList, votazioniEffettuateList;
	@FXML
	private Button votaButton;
	
	private Utente utente;
	
	public void sendData(Utente u) {
		this.utente = u;
		DbManager db = DbManager.getInstance();
		votaButton.setDisable(!UserManager.isValidVoter(utente.getCF()));
		
		List<VotazioneClassica> votazioni = db.getAllVotazioni();
		List<Referendum> referendum = db.getAllReferendum();
		List<VotazioneClassica> votazioniUtente = db.getVotazioniUtente(utente);
		List<Referendum> referendumUtente = db.getReferendumUtente(utente);
		
		for (VotazioneClassica vc : votazioni) {
			if (votazioniUtente.contains(vc) || DateUtils.hasEnded(vc.getFine())) {
				votazioniEffettuateList.getItems().add(vc);
			} else {
				votazioniAttiveList.getItems().add(vc);
			}
		}
		for (Referendum r : referendum) {
			if (referendumUtente.contains(r) || DateUtils.hasEnded(r.getFine())) {
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
	
	public void visualizza(ActionEvent event) {
		if (votazioniEffettuateList.getSelectionModel().getSelectedItem() == null) return;
		
		
		if (votazioniEffettuateList.getSelectionModel().getSelectedItem() instanceof Referendum) {
			Referendum r = (Referendum) votazioniEffettuateList.getSelectionModel().getSelectedItem();
			
			if (!DateUtils.hasEnded(r.getFine())) {
				Alert nonFinita = new Alert(AlertType.INFORMATION);
				nonFinita.setTitle("Esito referendum " + r.getNome());
				nonFinita.setHeaderText("Il referendum è ancora in corso");
				nonFinita.setContentText("La data di fine è " + r.getFine().toString());
				nonFinita.showAndWait();
				return;
			}
			
			String esito = VotesManager.getEsitoReferendum(r);
			
			Alert esitoAlert = new Alert(AlertType.INFORMATION);
			esitoAlert.setTitle("Esito referendum " + r.getNome());
			esitoAlert.setHeaderText(r.getQuesito());
			esitoAlert.setContentText("Esito: " + esito);
			esitoAlert.showAndWait();
		} else {
			VotazioneClassica vc = (VotazioneClassica) votazioniEffettuateList.getSelectionModel().getSelectedItem();
			
			if (!DateUtils.hasEnded(vc.getFine())) {
				Alert nonFinita = new Alert(AlertType.INFORMATION);
				nonFinita.setTitle("Esito referendum " + vc.getNome());
				nonFinita.setHeaderText("Il referendum è ancora in corso");
				nonFinita.setContentText("La data di fine è " + vc.getFine().toString());
				nonFinita.showAndWait();
				return;
			}
			
			
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
	
}
