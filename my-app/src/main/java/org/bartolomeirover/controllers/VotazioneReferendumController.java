package org.bartolomeirover.controllers;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Referendum;
import org.bartolomeirover.models.TipoEsitoRef;
import org.bartolomeirover.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class VotazioneReferendumController extends Controller {

	@FXML
	private Label quesitoLabel;
	@FXML
	private RadioButton yesRadio, noRadio;
	
	private Referendum ref;
	private Utente utente;
	
	public void sendData(Referendum r, Utente u) {
		this.ref = r;
		this.utente = u;
		quesitoLabel.setText(r.getQuesito());
	}
	
	public void confirm(ActionEvent event) {
		DbManager db = DbManager.getInstance();
		
		if (yesRadio.isSelected()) {
			db.registraEsitoReferendum(ref, TipoEsitoRef.FAVOREVOLE);
		} else if (noRadio.isSelected()) {
			db.registraEsitoReferendum(ref, TipoEsitoRef.CONTRARIO);
		} else {
			db.registraEsitoReferendum(ref, TipoEsitoRef.BIANCA);
		}
		
		db.registraVotoReferendum(utente, ref);
		
		navigate("PannelloUtente");
		PannelloUtenteController contr = (PannelloUtenteController) App.getController();
		contr.sendData(utente);
	}
	
	public void blank(ActionEvent event) {
		DbManager db = DbManager.getInstance();
		db.registraEsitoReferendum(ref, TipoEsitoRef.BIANCA);
		db.registraVotoReferendum(utente, ref);
	}
	
}
