package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import models.*;

public class DbManager {

		private static DbManager _instance;
		
		private static String dbUrl = "";
		private ConnectionSource source;
		
		private Dao<Utente, String> utenti;
		private Dao<VotazioneClassica, Integer> votazioni;
		private Dao<Referendum, Integer> referendums;
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
	            referendums = DaoManager.createDao(source, Referendum.class);
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
		
		
		public boolean aggiungiPartito(String nome) {
			Objects.requireNonNull(nome);
			try {
				partiti.createIfNotExists(new Partito(nome));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		public boolean aggiungiCandidato(String nome, String cognome, String nome_partito) {
			Objects.requireNonNull(nome);
			Objects.requireNonNull(cognome);
			Objects.requireNonNull(nome_partito);

			try {
				Candidato candidato = new Candidato(nome, cognome);
				Partito partito = partiti.queryForId(nome_partito);
				if(partito != null) {
					candidato.setPartito(partito);
					return true;
				}
				return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		public boolean registraPartecipazionePartito(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(partito);
			try {
				if (votiPartiti.queryForMatching(new VotiPartito(votazione, partito)) != null)
					return true;
				
				votiPartiti.create(new VotiPartito(votazione, partito));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/*
		public boolean controllaPartecipazionePartito(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			try {
				Map<String, Object> fields = new HashMap<>();
				fields.put("partito", partito);
				fields.put("votazione", votazione);
				if(votiPartiti.queryForFieldValues(fields) != null)
					return true;
				else
					return false;
			}catch(SQLException e) {
				return false;
			}
		}
		*/
		
		public VotazioneClassica registraVotoVotazione(Utente utente, int id_votazione) {
			// cerca referendum
			Objects.requireNonNull(utente);
			try {
				VotazioneClassica vc = votazioni.queryForId(id_votazione);
							
				if (vc != null) {
					votiVotazioni.create(new Voti(vc, utente));
					return vc;
				}
				return null;
			}catch(SQLException e) {
				return null;
			}
		}
		
		public Candidato getCandidato(int id_candidato) {
			try {
				return candidati.queryForId(id_candidato);
			}catch(SQLException e) {
				return null;
			}
		}
		
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Candidato candidato) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(candidato);
			try {
				 // da controllare il funzionamento
				List<VotiCandidato> vc = votiCandidati.queryForMatching(new VotiCandidato(votazione, candidato));
				vc.get(0).aggiungiVoto();
				
				if(votiCandidati.update(vc.get(0)) == 1)
					return true;
				else 
					return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		public Partito getPartito(String nome_partito) {
			Objects.requireNonNull(nome_partito);
			try {
				return partiti.queryForId(nome_partito);
			}catch(SQLException e) {
				return null;
			}
		}
		
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(partito);
			try {
				 // da controllare il funzionamento
				List<VotiPartito> vc = votiPartiti.queryForMatching(new VotiPartito(votazione, partito));
				vc.get(0).aggiungiVoto();
				
				if(votiPartiti.update(vc.get(0)) == 1)
					return true;
				else 
					return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		public Referendum registraVotoReferendum(Utente utente, int id_referendum) {
			Objects.requireNonNull(utente);
			try {
				Referendum referendum = referendums.queryForId(id_referendum);
				
				if (referendum != null) {
					votiReferendum.create(new VotiReferendum(referendum, utente));
					return referendum;
				}
				return null;
			}catch(SQLException e) {
				return null;
			}
		}
		
		public boolean registraEsitoReferendum(Referendum referendum, TipoEsitoRef esito) {
			Objects.requireNonNull(referendum);
			Objects.requireNonNull(esito);
			try {
				referendum.aggiungiVoto(esito);
				if(referendums.update(referendum) == 1)
					return true;
				else 
					return false;
			}catch(SQLException e) {
				return false;
			}
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
