package org.bartolomeirover.controllers;

import org.bartolomeirover.common.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController extends Controller {
	
	@FXML
	private Button exitButton;
	@FXML
	private Pane homePane;
	
	Stage stage;

	public void goToLogin(ActionEvent e) {
		navigate("Login");
	}
	
	public void goToAccess(ActionEvent e) {
		System.out.println("You cannot get access");
	}
	
	public void exit(ActionEvent e) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Chiusura programma");
		alert.setHeaderText("Stai per chiudere il programma di votazione");
		alert.setContentText("Sei sicuro?");
		
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) homePane.getScene().getWindow();
			System.out.println("You successfully exited");
			stage.close();
		}
		
	}
	
}
