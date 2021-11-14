package com.mycompany.voteApp.my_app;

/* OVERVIEW: classe che rappresenta un utente scrutinatore. prova cambio*/
public class Scrutinatore extends User{
	private String nominativo;
	private String codFiscale;
	
	public Scrutinatore(String username, String password, String seggio, String nominativo, String codFiscale) {
		this.username = username;
		this.password = password;
		this.seggio = seggio;
		this.nominativo = nominativo;
		this.codFiscale = codFiscale;
	}
}
