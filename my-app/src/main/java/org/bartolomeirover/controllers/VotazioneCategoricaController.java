package org.bartolomeirover.controllers;

import java.util.List;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;
import org.bartolomeirover.models.TipoVotazione;
import org.bartolomeirover.models.Utente;
import org.bartolomeirover.models.VotazioneClassica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class VotazioneCategoricaController extends Controller {

	@FXML
	private ComboBox<Object> sceltaCombo;
	
	private VotazioneClassica votazione;
	private Utente utente;
	
	public void sendData(VotazioneClassica vc, Utente u) {
		this.votazione = vc;
		this.utente = u;
		
		DbManager db = DbManager.getInstance();
		
		//riempi combobox
		switch(votazione.getTipo()) {
			case CATEGORICO_PARTITI:
			case CON_PREFERENZA:
				List<Partito> partiti = db.getVotazionePartito(votazione);
				sceltaCombo.getItems().addAll(partiti);
				break;
			case CATEGORICO_CANDIDATI:
				List<Candidato> candidati = db.getVotazioneCandidato(votazione);
				sceltaCombo.getItems().addAll(candidati);
				break;
			default:
				System.out.println("Unexpected TipoVotazione");
		}
	}
	
	public void confirm(ActionEvent event) {
		if (sceltaCombo.getValue() == null) return;
		
		DbManager db = DbManager.getInstance();
		switch(votazione.getTipo()) {
			case CATEGORICO_PARTITI:
			case CON_PREFERENZA:
				Partito p = (Partito) sceltaCombo.getValue();
				db.registraEsitoVotazione(votazione, p);
				break;
			case CATEGORICO_CANDIDATI:
				Candidato c = (Candidato) sceltaCombo.getValue();
				db.registraEsitoVotazione(votazione, c);
				break;
			default:
				System.out.println("Unexpected TipoVotazione");
		}
		if (votazione.getTipo() == TipoVotazione.CON_PREFERENZA) {
			navigate("VotazionePreferenza");
			VotazionePreferenzaController contr = (VotazionePreferenzaController) App.getController();
			contr.sendData(votazione, utente, (Partito) sceltaCombo.getValue());
		} else {
			db.registraVotoVotazione(utente, votazione);
			navigate("PannelloUtente");
			PannelloUtenteController contr = (PannelloUtenteController) App.getController();
			contr.sendData(utente);
		}
	}
	
	public void blank(ActionEvent event) {
		DbManager db = DbManager.getInstance();
		db.registraEsitoVotazione(this.votazione);
		db.registraVotoVotazione(this.utente, this.votazione);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(this.utente);
	}
	
}
