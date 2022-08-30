package data;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import models.*;

public class DbManager {

		private static DbManager _instance;
		
		private static String dbUrl = "";
		private ConnectionSource source;
		
		private Dao<Utente, String> utenti;
		private Dao<VotazioneClassica, Integer> votazioni;
		private Dao<Referendum, Integer> referendum;
		private Dao<Partito, String> partiti;
		private Dao<Candidato, Integer> candidati;
		
		private Dao<Voti, Integer> votiVotazioni;
		private Dao<VotiReferendum, Integer> votiReferendum;
		private Dao<VotiPartito, Integer> votiPartiti;
		private Dao<VotiCandidato, Integer> votiCandidati;
		
		
		private DbManager() {
			
		}
		
		public static DbManager getInstance() {
			
			if(_instance == null) {
				_instance = new DbManager();
			}
			return _instance;
		}
		
		
		/**
		 * DB utility methods 
		 */
		
		public boolean checkCreation() {
			
			if( !open() ) {
				return false;
			}
			
			try {
	            TableUtils.createTableIfNotExists(source, Utente.class);
	            TableUtils.createTableIfNotExists(source, VotazioneClassica.class);
	            TableUtils.createTableIfNotExists(source, Referendum.class);
	            TableUtils.createTableIfNotExists(source, Partito.class);
	            TableUtils.createTableIfNotExists(source, Candidato.class);
	            
	            TableUtils.createTableIfNotExists(source, Voti.class);
	            TableUtils.createTableIfNotExists(source, VotiReferendum.class);
	            TableUtils.createTableIfNotExists(source, VotiPartito.class);
	            TableUtils.createTableIfNotExists(source, VotiCandidato.class);

	            utenti = DaoManager.createDao(source, Utente.class);
	            votazioni = DaoManager.createDao(source, VotazioneClassica.class);
	            referendum = DaoManager.createDao(source, Referendum.class);
	            partiti = DaoManager.createDao(source, Partito.class);
	            votiVotazioni = DaoManager.createDao(source, Voti.class);
	            votiReferendum = DaoManager.createDao(source, VotiReferendum.class);
	            votiPartiti = DaoManager.createDao(source, VotiPartito.class);
	            votiCandidati= DaoManager.createDao(source, VotiCandidato.class);
	            

	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
			
		}
		
		public boolean open() {
	        try {
	            source = new JdbcConnectionSource(dbUrl);
	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
	    }
		
		public boolean close() {
	        try {
	            source.close();
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
		
		
		/**
		 *  DB operations
		 */
		
		
		/**
		 * 
		 * @param cf
		 * @param hashPassword
		 * @param nome
		 * @param cognome
		 * @return 1 se tupla aggiunta correttamente, 0 se utente già presente nel db, -1 se credenziali non approvate da AgiD,
		 * 				-2 se viene sollevata una SQLException. 
		 */
		public int registra(String cf, String hashPassword, String nome, String cognome) {
			try {
				Utente utente = new Utente(cf, hashPassword, nome, cognome, false);
				if(UserManager.isApprovedByAgiD(utente)) {
					utente.setAdmin(UserManager.isApprovedAdmin(utente.getCF()));
					
					if(utenti.queryForId(utente.getCF()) == null) {
						return utenti.create(utente); // forse si puo togliere if
					} else {
						return 0; // User already exists
					}	
				} else {
					return -1; // Credentials not approved
				}
			}catch(SQLException e) {
				return -2;
			}
		}
		
		
		/**
		 * 
		 * @param cf
		 * @param hashPassword
		 * @return utente associato alle credenziali se corrispondono ad un utente nel db, altrimenti null
		 */
		public Utente autentica(String cf, String hashPassword) {
			try {
				Utente utente = utenti.queryForId(cf);
				if(utente != null) {
					if (utente.checkPwd(hashPassword))
						return utente;
					else
						return null;
				} else {
					return null;
				}
			} catch(SQLException e) {
				return null;
			}
		}
		
		public boolean registraVotoVotazione(Utente utente, VotazioneClassica votazione) {
			// TODO
			return true;
		}
		
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Candidato candidato) {
			// TODO
			return true;
		}
		
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Partito partito) {
			// TODO
			return true;
		}
		
		public boolean registraVotoReferendum(Utente utente, Referendum referendum) {
			// TODO
			return true;
		}
		
		public boolean registraEsitoReferendum(Referendum referendum, TipoEsitoRef esito) {
			// TODO
			return true;
		}
		
		public List<VotazioneClassica> getVotazioniUtente(Utente utente){
			// TODO
			return null;
		}
		
		public List<Referendum> getReferendumUtente(Utente utente){
			// TODO
			return null;
		}
		
		
		
		// v magari in una nuova classe manager per gestire menu aggiunta candidati
		// add candidato
		// add partito
		
		
		
		
				
}
