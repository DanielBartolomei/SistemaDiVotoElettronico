package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;
import org.bartolomeirover.models.TipoVotazione;
import org.bartolomeirover.models.VotazioneClassica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class CreaAmministrativaPartitiController extends Controller implements Initializable {

	@FXML
	private ListView<Partito> partitiList, partecipantiList;
	
	private String nomeVotazione;
	private TipoVotazione tipoAmministrative;
	private LocalDate dataInizio, dataFine;
	private boolean isAssoluta;
	
	private List<Partito> possibili;
	
	public void sendData(String nomeVotazione, LocalDate dataInizio, LocalDate dataFine, boolean isAssoluta, 
			TipoVotazione tipoAmministrative) {
		this.nomeVotazione = nomeVotazione;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.isAssoluta = isAssoluta;
		this.tipoAmministrative = tipoAmministrative;
	}
	
	public void submit(ActionEvent event) {
		// 0. check partiti/candidati
		if (partecipantiList.getItems().size() == 0) return;
		
		// 1. Aggiungi votazione al db (senza partecipanti)
		DbManager db = DbManager.getInstance();
		VotazioneClassica vc = db.aggiungiVotazione(this.nomeVotazione, this.dataInizio, 
				this.dataFine, this.isAssoluta, this.tipoAmministrative);
		
		// 2. Registra partecipazione dei partiti/candidati alla votazione
		List<Partito> partecipanti = partecipantiList.getItems();
		for (Partito partito : partecipanti) {
			db.registraPartecipazionePartito(vc, partito);
			if (tipoAmministrative.toString().equals(TipoVotazione.CON_PREFERENZA.toString())) {
				List<Candidato> candidati = db.getListaCandidati(partito);
				for (Candidato candidato : candidati) {
					db.registraPartecipazioneCandidato(vc, candidato);
				}
			}
		}
		
		navigate("PannelloAdmin");
	}
	
	public void back(ActionEvent event) {
		navigate("PannelloAdmin");
	}
	
	public void insert(ActionEvent event) {
		Partito selezionato = partitiList.getSelectionModel().getSelectedItem();
		if (selezionato == null) return;
		if (!partecipantiList.getItems().contains(selezionato)) {
			partecipantiList.getItems().add(selezionato);
		}
	}
	
	public void delete(ActionEvent event) {
		if (partecipantiList.getSelectionModel().getSelectedItem() == null) return;
		partecipantiList.getItems().remove(partecipantiList.getSelectionModel().getSelectedItem());
	}

	private void populateList() {
		DbManager db = DbManager.getInstance();
		this.possibili = db.getAllPartito();
		partitiList.getItems().addAll(this.possibili);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		populateList();
		
	}
}
