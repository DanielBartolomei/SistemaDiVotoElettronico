package org.bartolomeirover.data;

import java.time.LocalDate;

import org.bartolomeirover.models.CodiceFiscale;
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
		LocalDate dataNascita = CodiceFiscale.getDataDiNascita(cf);
		LocalDate dataNascitaLimite = LocalDate.now().withYear(LocalDate.now().getYear() - 18);
		
		if(dataNascitaLimite.isAfter(dataNascita) || dataNascitaLimite.isEqual(dataNascita)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isApprovedAdmin(String cf) {
		return true; // simulato
	}

	public static boolean isApprovedByAgiD(Utente utente) {
		return true; // simulato
	}
}
