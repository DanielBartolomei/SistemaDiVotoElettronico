package com.mycompany.voteApp.my_app;

public class Elettore extends User{
	
	private String numTesseraElet;
	
	public Elettore(String username, String password, String seggio, String numTesseraElet) {
		this.username = username;
		this.password = password;
		this.seggio = seggio;
		this.numTesseraElet = numTesseraElet;
	}

}
