package org.bartolomeirover.data;

import org.bartolomeirover.models.Utente;

public class UserManager {

	private UserManager _instance;
	
	private UserManager() {
		
	}
	
	public UserManager getInstance() {
		if(_instance == null)
			_instance = new UserManager();
		return _instance;
	}
	
	/**
	 * TODO
	 * @param cf
	 * @return
	 */
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
