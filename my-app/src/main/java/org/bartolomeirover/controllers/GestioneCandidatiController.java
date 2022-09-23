package org.bartolomeirover.controllers;

import java.util.List;

import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Candidato;
import org.bartolomeirover.models.Partito;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GestioneCandidatiController extends Controller {

	@FXML
	private ListView<Candidato> candidatiList;
	@FXML
	private TextField nomeField, cognomeField;
	@FXML
	private Label titoloLabel;
	
	private Partito ref;
	
	public void sendData(Partito p) {
		this.ref = p;
		titoloLabel.setText("Gestione candidati - " + ref.getNome());
		
		DbManager db = DbManager.getInstance();
		List<Candidato> candidati = db.getListaCandidati(ref);
		candidatiList.getItems().addAll(candidati);
	}
	
	public void confirm(ActionEvent event) {
		navigate("GestionePartiti");
	}
	
	public void addCandidato(ActionEvent event) {
		if (nomeField.getText() == null || nomeField.getText().equals("")) return;
		if (cognomeField.getText() == null || cognomeField.getText().equals("")) return;
		
		DbManager db = DbManager.getInstance();
		db.aggiungiCandidato(nomeField.getText(), cognomeField.getText(), ref.getNome());
		
		candidatiList.getItems().add(new Candidato(nomeField.getText(), cognomeField.getText(), ref));
	}
	
	public void deleteCandidato(ActionEvent event) {
		if (candidatiList.getSelectionModel().getSelectedItem() == null) return;
		
		DbManager db = DbManager.getInstance();
		db.rimuoviCandidato(candidatiList.getSelectionModel().getSelectedItem());
		candidatiList.getItems().remove(candidatiList.getSelectionModel().getSelectedItem());
	}
}
