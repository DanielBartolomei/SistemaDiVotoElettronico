package org.bartolomeirover.data;

import java.time.LocalDate;

import org.bartolomeirover.models.CodiceFiscale;
import org.bartolomeirover.models.Utente;

public class UserManager {

	private static UserManager _instance;
	
	private UserManager() {
		
	}
	
	/**
	 * Restituisce l'istanza del Singleton UserManager. Se non è presente, ne crea una.s
	 * @return l'istanza di UserManager
	 */
	public static UserManager getInstance() {
		if(_instance == null)
			_instance = new UserManager();
		return _instance;
	}
	
	/**
	 * Indica se un utente è idoneo al voto.
	 * @param cf
	 * @return true se la persona associata al codice fiscale è maggiorenne, false altrimenti.
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
	
	/**
	 * Assegna ad un utente il livello di permesso previsto da AgID.
	 * E' simulato e ritorna sempre true.
	 * @param cf
	 * @return true se la persona associata al <b>cf</b> è un admin approvato da AgID. 
	 */
	public static boolean isApprovedAdmin(String cf) {
		return true; // simulato
	}

	/**
	 * Indica se un utente è idoneo ad usare la piattaforma.
	 * E' simulato e ritorna sempre true.
	 * @param utente
	 * @return true se la persona associata al <b>cf</b> è un utente riconosciuta da AgID.
	 */
	public static boolean isApprovedByAgiD(Utente utente) {
		return true; // simulato
	}
}
