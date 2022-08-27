package data;

import models.CodiceFiscale;

public class UserManager {

	private UserManager _instance;
	
	private UserManager() {
		
	}
	
	public UserManager getInstance() {
		if(_instance == null)
			_instance = new UserManager();
		return _instance;
	}
	
	public boolean isValidVoter(CodiceFiscale cf){
		// TODO
		return true;
	}
	
	public boolean isApprovedAdmin(CodiceFiscale cf) {
		return true; // simulato
	}
}
