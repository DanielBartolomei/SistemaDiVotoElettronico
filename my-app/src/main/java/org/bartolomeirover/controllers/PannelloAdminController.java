package org.bartolomeirover.controllers;

import org.bartolomeirover.common.Controller;

import javafx.event.ActionEvent;

public class PannelloAdminController extends Controller {
	
	public void logout(ActionEvent event) {
		navigate("Home");
	}
	
	public void goToCreaVotazione(ActionEvent event) {
		navigate("CreaVotazione1");
	}
}
