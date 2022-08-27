package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class LoginController {
	
	private String username;
	private String password;
	private boolean isAdmin;
	
	public void login(ActionEvent event) {
		System.out.println("You pressed the login button");
	}
	
}
