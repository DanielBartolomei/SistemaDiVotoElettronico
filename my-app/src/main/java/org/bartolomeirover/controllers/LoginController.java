package org.bartolomeirover.controllers;

import java.nio.charset.StandardCharsets;

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
		System.out.println("You pressed the login button");
		
		DbManager db = DbManager.getInstance();
		
		Utente toBeLogged = db.autentica(usernameField.getText(), Hashing.sha256().hashString(passwordField.getText() , StandardCharsets.UTF_8).toString());
		if (toBeLogged == null) {
			System.out.println("Utente non trovato");
			usernameField.setText("");
			passwordField.setText("");
			adminCheck.setSelected(false);
		} else {
			if (adminCheck.isSelected() && toBeLogged.isAdmin()) {
				navigate("PannelloAdmin");
			} else {
				navigate("PannelloUtente");
			}
		}
	}
	
	public void back(ActionEvent event) {
		navigate("Home");
	}
	
}
