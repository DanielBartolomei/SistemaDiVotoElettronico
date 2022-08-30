package data;

import models.CodiceFiscale;
import models.Utente;

public class UserManager {

	private UserManager _instance;
	
	private UserManager() {
		
	}
	
	public UserManager getInstance() {
		if(_instance == null)
			_instance = new UserManager();
		return _instance;
	}
	
	public static boolean isValidVoter(String cf){
		// TODO
		return true;
	}
	
	public static boolean isApprovedAdmin(String cf) {
		return true; // simulato
	}

	public static boolean isApprovedByAgiD(Utente utente) {
		return true; // simulato
	}
}
