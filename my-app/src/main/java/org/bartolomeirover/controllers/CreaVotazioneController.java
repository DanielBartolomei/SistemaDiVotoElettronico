package org.bartolomeirover.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CreaVotazioneController extends Controller implements Initializable {

	@FXML
	private DatePicker fineDate, inizioDate;
	@FXML
	private TextField nomeField;
	@FXML
	private CheckBox quorumCheck, maggioranzaAssolutaCheck;
	@FXML
	private ComboBox<String> tipoAmministrativeCombo;
	@FXML
	private Button avantiButton;
	@FXML
	private RadioButton referendumRadio, amministrativeRadio;
	
	private String[] tipi = {"Categorica (partiti)", "Categorica (candidati)", "Ordinale (partiti)", "Ordinale (candidati)"
			, "Categorica + preferenza"};
	
	
	public void next(ActionEvent event) {
		
		if (amministrativeRadio.isSelected()) {
			navigate("CreaAmministrativa");
			CreaAmministrativaController contr = (CreaAmministrativaController) App.getController();
			contr.sendData(nomeField.getText(), inizioDate.getValue(), fineDate.getValue(), 
					maggioranzaAssolutaCheck.isSelected(), tipoAmministrativeCombo.getValue());
		} else {
			
		}
	}
	
	public void back(ActionEvent event) {
		navigate("PannelloAdmin");
	}
	
	public void cambioTipoVotazione(ActionEvent event) {
		avantiButton.setDisable(!checkDataInputCompletion());
		
		if (referendumRadio.isSelected()) {
			tipoAmministrativeCombo.setDisable(true);
			maggioranzaAssolutaCheck.setDisable(true);
			quorumCheck.setDisable(false);
		} else {
			tipoAmministrativeCombo.setDisable(false);
			maggioranzaAssolutaCheck.setDisable(false);
			quorumCheck.setDisable(true);
		}
	}
	
	public void unlockAvanti(ActionEvent event) {
		avantiButton.setDisable(!checkDataInputCompletion());
	}
	
	// nomeField onTextChange
	public void unlockAvantiText(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		avantiButton.setDisable(newValue == null || newValue.equals("") || !checkDataInputCompletion());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tipoAmministrativeCombo.getItems().addAll(tipi);
		System.out.println(tipoAmministrativeCombo.getValue());
	}
	
	private boolean checkDataInputCompletion() {
		if (nomeField.getText().equals("") || nomeField.getText() == null) return false;
		
		if (fineDate.getValue() == null || inizioDate.getValue() == null) return false;
		if (inizioDate.getValue().compareTo(LocalDate.now()) < 0) return false;
		if (inizioDate.getValue().compareTo(fineDate.getValue()) >= 0) return false;
		
		if (amministrativeRadio.isSelected()) {
			if (tipoAmministrativeCombo.getValue() == null) return false;
		}
		
		return true;
	}
}
