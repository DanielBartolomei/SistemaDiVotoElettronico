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
		
		
		public Utente registra(CodiceFiscale cf, String hashPassword, String nome, String cognome, boolean isAdmin) {
			// TODO
			return null;
		}
		
		public Utente autentica(CodiceFiscale cf, String hashPassword) {
			// TODO
			return null;
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
		
		// get votazioni utente
		// get referendum utente
		
		// v magari in una nuova classe manager per gestire menu aggiunta candidati
		// add candidato
		// add partito
		
		
		
		
		
		
		// TODO
		// utente
		
		public Utente getUtente(String cf) {
			return null;
		}
		
		public List<Utente> getUtenti(){
			return null;
		}
		
		// votazione classica
		
		public VotazioneClassica getVotazione(int id) {
			return null;
		}
		
		public List<VotazioneClassica> getVotazioni() {
			return null;
		}
		
		// referendum
		
		public Referendum getReferendum(int id) {
			return null;
		}
		
		public List<Referendum> getReferendums() {
			return null;
		}
		
		// partito
		
		public Partito getPartito(String nome) {
			return null;
		}
		
		public List<Partito> getPartiti () {
			return null;
		}
		
		// candidato		
		
		public Candidato getCandidato(int id) {
			return null;
		}
		
		public List<Candidato> getCandidati() {
			return null;
		}
		
		
		
		
		
		
		
		
}
