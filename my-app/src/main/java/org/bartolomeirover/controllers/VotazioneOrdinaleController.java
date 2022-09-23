package org.bartolomeirover.controllers;

import java.util.ArrayList;
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
import javafx.scene.control.ListView;

public class VotazioneOrdinaleController extends Controller {

	@FXML
	private ListView<Object> partecipantiList, votatiList;
	
	private Utente utente;
	private VotazioneClassica votazione;
	
	public void sendData(VotazioneClassica vc, Utente u) {
		this.utente = u;
		this.votazione = vc;
		
		DbManager db = DbManager.getInstance();
		
		//popola lista
		switch(votazione.getTipo()) {
		case ORDINALE_PARTITI:
			List<Partito> partiti = db.getVotazionePartito(this.votazione);
			partecipantiList.getItems().addAll(partiti);
			break;
		case ORDINALE_CANDIDATI:
			List<Candidato> candidati = db.getVotazioneCandidato(this.votazione);
			partecipantiList.getItems().addAll(candidati);
			break;
		default:
			System.out.println("Errore tipo");
		}
	}
	
	public void insertVotato(ActionEvent event) {
		if (partecipantiList.getSelectionModel().getSelectedItem() == null) return;
		
		if (partecipantiList.getSelectionModel().getSelectedItem() instanceof Partito) {
			List<Partito> app = new ArrayList<>();
			for (Object o : votatiList.getItems()) {
				app.add((Partito) o);
			}
			if (!app.contains((Partito) partecipantiList.getSelectionModel().getSelectedItem())) {
				votatiList.getItems().add(partecipantiList.getSelectionModel().getSelectedItem());
			}
		} else {
			List<Candidato> app = new ArrayList<>();
			for (Object o : votatiList.getItems()) {
				app.add((Candidato) o);
			}
			if (!app.contains((Candidato) partecipantiList.getSelectionModel().getSelectedItem())) {
				votatiList.getItems().add(partecipantiList.getSelectionModel().getSelectedItem());
			}
		}
	}
	
	public void deleteVotato(ActionEvent event) {
		if (votatiList.getSelectionModel().getSelectedItem() == null) return;
		
		votatiList.getItems().remove(votatiList.getSelectionModel().getSelectedItem());
	}
	
	public void blank(ActionEvent event) {
		DbManager db = DbManager.getInstance();
		db.registraVotoVotazione(this.utente, this.votazione);
		db.registraEsitoVotazione(this.votazione);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(this.utente);
	}
	
	public void confirm(ActionEvent event) {
		if (votatiList.getItems().size() != partecipantiList.getItems().size()) return;
		DbManager db = DbManager.getInstance();
		
		if (votatiList.getItems().get(0) instanceof Partito) {
			List<Partito> app = new ArrayList<>();
			for (Object o : votatiList.getItems()) {
				app.add((Partito) o);
			}
			int amount = 1;
			for (Partito p : app) {
				db.registraEsitoVotazione(this.votazione, p, amount);
				amount++;
			}
		} else {
			List<Candidato> app = new ArrayList<>();
			for (Object o : votatiList.getItems()) {
				app.add((Candidato) o);
			}
			int amount = 1;
			for (Candidato c : app) {
				db.registraEsitoVotazione(this.votazione, c, amount);
				amount++;
			}
		}
		
		db.registraVotoVotazione(this.utente, this.votazione);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(this.utente);
		
	}
}
