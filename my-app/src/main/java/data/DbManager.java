package data;

import java.util.List;
import java.util.Objects;
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
		
		/**
		 * @return true se tabelle sono state create correttamente e i loro rispettivi DAO,
		 * 		false se non è stato possibile collegarsi al db o se è sollevata una SQLException.
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
		
		/**
		 * @return true se connessione al db stabilita correttamente,
		 * 		false se sollevata SQLException.
		 */
		public boolean open() {
	        try {
	            source = new JdbcConnectionSource(dbUrl);
	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
	    }
		
		/**
		 * @return true se connessione al db chiusa correttamente,
		 * 		false se sollevata SQLException.
		 */
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
		 * @return utente registrato se tupla aggiunta correttamente o se già presente nel db, 
		 * 		null se credenziali non approvate da AgiD o se è sollevata una SQLException.
		 * @throws NullPointerException se <b>cf</b>, <b>hashPassword</b>, <b>nome</b>, <b>cognome</b> sono riferimenti a null.
		 */
		public Utente registra(String cf, String hashPassword, String nome, String cognome) {
			Objects.requireNonNull(cf);
			Objects.requireNonNull(hashPassword);
			Objects.requireNonNull(nome);
			Objects.requireNonNull(cognome);
			try {
				Utente utente = new Utente(cf, hashPassword, nome, cognome, false);
				if(UserManager.isApprovedByAgiD(utente)) {
					utente.setAdmin(UserManager.isApprovedAdmin(utente.getCF()));					
					return utenti.createIfNotExists(utente);
				}else
					return null;

			}catch(SQLException e) {
				return null;
			}
		}
		
		
		/**
		 * 
		 * @param cf
		 * @param hashPassword
		 * @return utente associato alle credenziali se corrispondono ad un utente nel db, altrimenti null
		 * @throws NullPointerException se <b>cf</b> o <b>hashPassword</b> sono riferimenti a null.
		 */
		public Utente autentica(String cf, String hashPassword) {
			Objects.requireNonNull(cf);
			Objects.requireNonNull(hashPassword);
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
		
		
		/**
		 * 
		 * @param nome
		 * @return true se la creazione del partito è andata a buon fine o già era presente,
		 * 		 false se è stata sollevata una SQLException.
		 * @throws NullPointerException se <b>nome</b> è un riferimento a null.
		 */
		public boolean aggiungiPartito(String nome) {
			Objects.requireNonNull(nome);
			try {
				partiti.createIfNotExists(new Partito(nome));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param nome
		 * @param cognome
		 * @param nome_partito
		 * @return true se la creazione del candidato è andata a buon fine, 
		 * 		false se nome_partito non è associato ad un partito o se è sollevata una SQLException
		 * @throws NullPointerException se <b>nome</b>, <b>cognome</b> o <b>nome_partito</b> sono riferimenti a null.
		 */
		public boolean aggiungiCandidato(String nome, String cognome, String nome_partito) {
			Objects.requireNonNull(nome);
			Objects.requireNonNull(cognome);
			Objects.requireNonNull(nome_partito);

			try {
				Candidato candidato = new Candidato(nome, cognome);
				Partito partito = partiti.queryForId(nome_partito);
				if(partito != null) {
					candidato.setPartito(partito);
					partito.aggiungiCandidato(candidato);
					partiti.update(partito);
					candidati.createIfNotExists(candidato);
					return true;
				}
				return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param votazione
		 * @param partito
		 * @return true se il partito è stato aggiungo alla votazione o era già partecipante,
		 * 		false se viene sollevata una SQLException.
		 * @throws NullPointerException se <b>votazione</b> o <b>partito</b> sono riferimento a null.
		 */
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
		
		/**
		 * 
		 * @param utente
		 * @param id_votazione
		 * @return true se votazione registrata correttamente,
		 * 		false se utente ha gia votato o se solleva SQLException.
		 * @throws NullPointerException se <b>utente</b> o <b>votazione</b> sono riferimenti a null. 
		 */
		public boolean registraVotoVotazione(Utente utente, VotazioneClassica votazione) throws SQLException {
			Objects.requireNonNull(utente);
			Objects.requireNonNull(votazione);
			try {
				if(votiVotazioni.queryForMatching(new Voti(votazione, utente)) != null)
					return false;
				
				votiVotazioni.createIfNotExists(new Voti(votazione, utente));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		
		
		/**
		 * 
		 * @param votazione
		 * @param candidato
		 * @return true se il voto al candidato è stato registrato, 
		 * 		false se non ci sono tuple corrispondenti o se solleva SQLException.
		 * @throws NullPointerExceptionse <b>votazione</b> o <b>candidato</b> sono riferimetni a null.
		 */
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Candidato candidato) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(candidato);
			try {
				 // da controllare il funzionamento
				List<VotiCandidato> vc = votiCandidati.queryForMatching(new VotiCandidato(votazione, candidato));
				
				if(vc.size() != 0) {
					vc.get(0).aggiungiVoto();
					votiCandidati.update(vc.get(0));
					return true;
				}else
					return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		
		
		/**
		 * 
		 * @param votazione
		 * @param partito
		 * @return true se voto al partito registrato correttamente, 
		 * 			false se non ci sono tuple corrispondenti o solleva SQLException.
		 * @throws NullPointerExceptionse <b>votazione</b> o <b>partito</b> sono riferimetni a null.
		 */
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(partito);
			try {
				 // da controllare il funzionamento
				List<VotiPartito> vc = votiPartiti.queryForMatching(new VotiPartito(votazione, partito));
				
				if(vc.size() != 0) {
					vc.get(0).aggiungiVoto();
					votiPartiti.update(vc.get(0));
					return true;
				}else
					return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param utente
		 * @param id_referendum
		 * @return true se votazione registrata correttamente, false se utente ha gia votato al referendum o se solleva SQLException
		 * @throws NullPointerExceptionse <b>utente</b> o <b>referendum</b> sono riferimetni a null.
		 */
		public boolean registraVotoReferendum(Utente utente, Referendum referendum) {
			Objects.requireNonNull(utente);
			Objects.requireNonNull(referendum);
			try {
				if(votiReferendum.queryForMatching(new VotiReferendum(referendum, utente)) != null)
					return false;
				
				votiReferendum.createIfNotExists(new VotiReferendum(referendum, utente));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param referendum
		 * @param esito
		 * @return true se voto è stato registrato correttamente, false se solleva SQLException.
		 * @throws NullPointerExceptionse <b>esito</b> o <b>referendum</b> sono riferimetni a null.
		 */
		public boolean registraEsitoReferendum(Referendum referendum, TipoEsitoRef esito) {
			Objects.requireNonNull(referendum);
			Objects.requireNonNull(esito);
			try {
				referendum.aggiungiVoto(esito);
				referendums.update(referendum);
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param nome_partito
		 * @return partito associato a nome_partito, null se non corrisponde a nessuno o solleva SQLException.
		 * @throws NullPointerException se <b>nome_partito</b> è riferimento a null.
		 */
		public Partito getPartito(String nome_partito) {
			Objects.requireNonNull(nome_partito);
			try {
				return partiti.queryForId(nome_partito);
			}catch(SQLException e) {
				return null;
			}
		}
		
		/**
		 * 
		 * @param id_candidato
		 * @return candidato corrispondente al id_candidato, 
		 * 		null se l'id non è associato ad un candidato o è sollevata una SQLException
		 */
		public Candidato getCandidato(int id_candidato) {
			try {
				return candidati.queryForId(id_candidato);
			}catch(SQLException e) {
				return null;
			}
		}
		
		/**
		 * 
		 * @param nome_partito
		 * @return lista dei candidati dato il loro nome, 
		 * 		null se il nome non corrisponde a nessun partito o se solleva SQLException.
		 * @throws NullPointerException se <b>nome_partito</b> è riferimento a null.
		 */
		public List<Candidato> getListaCandidati(String nome_partito){
			Objects.requireNonNull(nome_partito);
			try {
				Partito partito = partiti.queryForId(nome_partito);
				if(partito != null) 
					return getListaCandidati(partito);
				else
					return null;
				
			}catch(SQLException e) {
				return null;
			}
		}
		
		/**
		 * 
		 * @param partito
		 * @return lista dei candidati dato l'oggetto partito, null se sollevata SQLException
		 * @throws NullPointerException se <b>partito</b> è riferimento a null.
		 */
		public List<Candidato> getListaCandidati(Partito partito){
			Objects.requireNonNull(partito);
			try {
				partiti.refresh(partito);
				return partito.getCandidati();
			}catch(SQLException e) {
				return null;
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
		
		/*
	 	public void createFakeData() {
        try {

            Totem t1 = new Totem("Via Adios 14", 0, 0);
            Totem t2 = new Totem("Via Vamos 12", 1, 2);

            totems.create(t1);
            totems.create(t2);

            Grip g11 = new Grip(t1, BikeType.standard, 0);
            Grip g12 = new Grip(t1, BikeType.electric, 1);
            Grip g13 = new Grip(t1, BikeType.electricBabySeat, 2);

            Grip g21 = new Grip(t2, BikeType.standard, 0);
            Grip g22 = new Grip(t2, BikeType.electric, 1);
            Grip g23 = new Grip(t2, BikeType.electricBabySeat, 2);

            Bike b1 = new Bike(BikeType.standard);
            Bike b2 = new Bike(BikeType.electric);
            Bike b3 = new Bike(BikeType.electricBabySeat);

            bikes.create(b1);
            bikes.create(b2);
            bikes.create(b3);

            grips.create(g11);
            grips.create(g12);
            grips.create(g13);
            grips.create(g21);
            grips.create(g22);
            grips.create(g23);

            g11.setBike(b1);
            g12.setBike(b2);
            g13.setBike(b3);

            b1.setGrip(g11);
            b2.setGrip(g12);
            b3.setGrip(g13);

            grips.update(g11);
            grips.update(g12);
            grips.update(g13);

            bikes.update(b1);
            bikes.update(b2);
            bikes.update(b3);

            Card card = new Card("1111111111111111", 444, DateUtils.oneYear());

            cards.create(card);

            Subscription admin = new Subscription("admin", SubscriptionType.admin, false);
            Subscription normal = new Subscription("normal", SubscriptionType.week, false);
            Subscription student = new Subscription("student", SubscriptionType.week, true);
            Subscription expired = new Subscription("expired", SubscriptionType.day, true);
            expired.setNumberOfExceed(3);

            admin.setCard(card);
            normal.setCard(card);
            student.setCard(card);
            expired.setCard(card);

            subscriptions.create(admin);
            subscriptions.create(normal);
            subscriptions.create(student);
            subscriptions.create(expired);
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
		 */
		
		
				
}
