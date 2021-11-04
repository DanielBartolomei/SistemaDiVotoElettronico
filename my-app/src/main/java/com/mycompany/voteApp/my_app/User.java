package com.mycompany.voteApp.my_app;

/* OVERVIEW: classe che rappresenta un utente generale. */
public abstract class User {
	
	protected String username;
	protected String password;
	protected String seggio;
	
	public boolean login(String usrn, String pwd) {
		return usrn == username && pwd == password;
	}
}
