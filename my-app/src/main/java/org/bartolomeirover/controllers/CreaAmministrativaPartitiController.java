package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.bartolomeirover.common.Controller;
import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.data.DbManager;
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
		// 0. check partiti/candidati not null nelle liste
		
		// 1. Aggiungi votazione al db (senza partecipanti)
		DbManager db = DbManager.getInstance();
		db.aggiungiVotazione(this.nomeVotazione, this.dataInizio, this.dataFine, this.isAssoluta, this.tipoAmministrative);
		
		// 2. Crea oggetto votazione
		VotazioneClassica vc = new VotazioneClassica(this.nomeVotazione, DateUtils.fromLocalDateToString(this.dataInizio), 
				DateUtils.fromLocalDateToString(this.dataFine), this.isAssoluta, this.tipoAmministrative);
		
		// 3. Registra partecipazione dei partiti alla votazione
		List<Partito> partecipanti = partecipantiList.getItems();
		
	}
	
	public void back(ActionEvent event) {
		navigate("PannelloAdmin");
	}
	
	public void insert(ActionEvent event) {
		
	}
	
	public void delete(ActionEvent event) {
		
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
