package org.bartolomeirover.controllers;

import org.bartolomeirover.common.Controller;
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
		
		// 1.  check credential on db
		// 2.  switch scene based on db check result
		// 2b. if login not successful, maybe change form style 
	}
	
}
