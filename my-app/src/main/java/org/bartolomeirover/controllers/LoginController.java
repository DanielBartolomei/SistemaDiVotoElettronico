package org.bartolomeirover.controllers;

import java.nio.charset.StandardCharsets;

import org.bartolomeirover.App;
import org.bartolomeirover.common.Controller;
import org.bartolomeirover.data.DbManager;
import org.bartolomeirover.models.Utente;

import com.google.common.hash.Hashing;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginController extends Controller {
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private CheckBox adminCheck;
	@FXML
	private Label titleLabel;
	
	public void login(ActionEvent event) {
		
		
		if (usernameField.getText().equals("")) {
			usernameField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
		} else {
			usernameField.setStyle("");
		}
		if (passwordField.getText().equals("")) {
			passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
		} else {
			passwordField.setStyle("");
		}
		
		DbManager db = DbManager.getInstance();
		
		Utente toBeLogged = db.autentica(usernameField.getText().toUpperCase(), Hashing.sha256().hashString(passwordField.getText() , StandardCharsets.UTF_8).toString());
		if (toBeLogged == null) {
			System.out.println("Utente non trovato");
			passwordField.setText("");
			adminCheck.setSelected(false);
			// show user not found on form
		} else {
			if (adminCheck.isSelected() && toBeLogged.isAdmin()) {
				navigate("PannelloAdmin");
			} else {
				String cf = usernameField.getText();
				navigate("PannelloUtente");
				PannelloUtenteController contr = (PannelloUtenteController) App.getController();
				contr.sendData(cf);
			}
		}
	}
	
	public void back(ActionEvent event) {
		navigate("Home");
	}
	
}
