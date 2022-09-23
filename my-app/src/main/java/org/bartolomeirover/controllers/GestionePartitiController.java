package org.bartolomeirover.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Partito;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GestionePartitiController extends Controller implements Initializable {

	@FXML
	private ListView<Partito> partitiList;
	@FXML
	private TextField nomeField;
	
	public void addPartito(ActionEvent event) {
		if (nomeField.getText() == null || nomeField.getText().equals("")) return;
		
		DbManager db = DbManager.getInstance();
		db.aggiungiPartito(nomeField.getText());
		
		partitiList.getItems().add(new Partito(nomeField.getText()));
	}
	
	public void deletePartito(ActionEvent event) {
		if (partitiList.getSelectionModel().getSelectedItem() == null) return;
		
		DbManager db = DbManager.getInstance();
		db.rimuoviPartito(partitiList.getSelectionModel().getSelectedItem());
		
		partitiList.getItems().remove(partitiList.getSelectionModel().getSelectedItem());
	}
	
	public void candidati(ActionEvent event) {
		if (partitiList.getSelectionModel().getSelectedItem() == null) return;
		
		navigate("GestioneCandidati");
		GestioneCandidatiController contr = (GestioneCandidatiController) App.getController();
		contr.sendData(partitiList.getSelectionModel().getSelectedItem());
	}
	
	public void back(ActionEvent event) {
		navigate("PannelloAdmin");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		DbManager db = DbManager.getInstance();
		List<Partito> partiti = db.getAllPartito();
		
		partitiList.getItems().addAll(partiti);
	}

}
