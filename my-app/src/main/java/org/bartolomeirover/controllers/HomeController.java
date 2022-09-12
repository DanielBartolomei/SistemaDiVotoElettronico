package org.bartolomeirover.controllers;

import org.bartolomeirover.common.Controller;
import javafx.event.ActionEvent;

public class HomeController extends Controller{

	public void goToLogin(ActionEvent e) {
		navigate("Login");
	}
	
	public void goToAccess(ActionEvent e) {
		System.out.println("You cannot get access");
	}
	
	public void exit(ActionEvent e) {
		System.out.println("You cannot escape");
	}
	
}
