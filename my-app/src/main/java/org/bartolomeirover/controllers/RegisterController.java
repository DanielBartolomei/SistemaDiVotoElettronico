package org.bartolomeirover.controllers;

import java.nio.charset.StandardCharsets;

import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Utente;

import com.google.common.hash.Hashing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends Controller {
	
	@FXML
	private TextField nomeField;
	@FXML
	private TextField cognomeField;
	@FXML
	private TextField cfField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordRepeatField;
	

	public void register(ActionEvent event) {
		
		passwordField.setText("");
		passwordRepeatField.setText("");
		
		if (!passwordField.getText().equals(passwordRepeatField.getText())) {
			System.out.println("Le password non combaciano!");
			return;
		}
		
		DbManager db = DbManager.getInstance();
		
		Utente toBeRegistered = db.registra(cfField.getText(), Hashing.sha256().hashString(passwordField.getText(), StandardCharsets.UTF_8).toString(), nomeField.getText(), cognomeField.getText());
		if (toBeRegistered == null) {
			System.out.println("Errore nella registrazione");
			return;
		}
		
		System.out.println("Utente registrato: " + toBeRegistered.getCF());
	}
	
	public void back(ActionEvent event) {
		navigate("Home");
	}
	
}
