package org.bartolomeirover.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;

import com.google.common.hash.Hashing;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.bartolomeirover.models.*;

public class DbManager {

		private static DbManager _instance;
		
		private static String dbUrl = "jdbc:sqlite:data.db"; //"jdbc:mysql://javauser:javauser123@localhost:3306/db";
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
	            candidati = DaoManager.createDao(source, Candidato.class);
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
		 * ######### DB operations #########
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
				Utente utente = new Utente(cf, hashPassword, nome, cognome);
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
		 * ######### Data Creation Operations #########
		 */
		
		
		/**
		 * 
		 * @param nome
		 * @return true se la creazione del partito è andata a buon fine,
		 * 		 false se già era presente o è stata sollevata una SQLException.
		 * @throws NullPointerException se <b>nome</b> è un riferimento a null.
		 */
		public boolean aggiungiPartito(String nome) {
			Objects.requireNonNull(nome);
			try {
				partiti.createIfNotExists(new Partito(nome));
				return false;
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
				Partito partito = partiti.queryForId(nome_partito);
				if(partito != null) {
					partito.aggiungiCandidato(new Candidato(nome, cognome));
					partiti.update(partito);
					//candidati.create(new Candidato(nome, cognome, partito));
					return true;
				}
				return false;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param nome
		 * @param dataInizio
		 * @param dataFine
		 * @param isAssoluta
		 * @param tipoVotazione
		 * @return true se la votazione è stata aggiunta correttamente, 
		 * 		false se una votazione con le stesse caratteristiche è gia presente 
		 * 			o se è stata sollevata una SQLException.
		 * @throws NullPointerException se <b>nome</b>, <b>dataInizio</b>, 
		 * 			<b>dataFine</b> o <b>tipoVotazione</b> sono riferimenti a null.
		 */
		public boolean aggiungiVotazione(String nome, Date dataInizio, 
				Date dataFine, boolean isAssoluta, TipoVotazione tipoVotazione) {
			Objects.requireNonNull(nome);
			Objects.requireNonNull(dataInizio);
			Objects.requireNonNull(dataFine);
			Objects.requireNonNull(tipoVotazione);
			
			try {
				if (votazioni.queryForMatching(new VotazioneClassica(nome, dataInizio, 
						dataFine, isAssoluta, tipoVotazione)).size() > 0)
					return false;
					
				votazioni.create(new VotazioneClassica(nome, dataInizio, dataFine, isAssoluta, tipoVotazione));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * @param nome
		 * @param dataInizio
		 * @param dataFine
		 * @param hasQuorum
		 * @return true se la votazione è stata aggiunta correttamente, 
		 * 		false se una votazione con le stesse caratteristiche è gia presente 
		 * 			o se è stata sollevata una SQLException.
		 * @throws NullPointerException se <b>nome</b>, <b>dataInizio</b> o <b>dataFine</b> sono riferimenti a null.
		 */
		public boolean aggiungiReferendum(String nome, Date dataInizio, 
				Date dataFine, boolean hasQuorum) {
			Objects.requireNonNull(nome);
			Objects.requireNonNull(dataInizio);
			Objects.requireNonNull(dataFine);
			
			try {
				if (referendums.queryForMatching(new Referendum(nome, dataInizio, dataFine, hasQuorum)).size() > 0)
					return false;
				
				referendums.create(new Referendum(nome, dataInizio, dataFine, hasQuorum));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		
		/**
		 * ######### Votes Operations #########
		 */
		
		/**
		 * 
		 * @param votazione
		 * @param partito
		 * @return true se il partito è stato aggiungo alla votazione,
		 * 		false se era già partecipante o viene sollevata una SQLException.
		 * @throws NullPointerException se <b>votazione</b> o <b>partito</b> sono riferimento a null.
		 */
		public boolean registraPartecipazionePartito(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(partito);
			try {
				
				if (votiPartiti.queryForMatching(new VotiPartito(votazione, partito)).size() > 0)
					return false;
				
				votiPartiti.create(new VotiPartito(votazione, partito));
				return true;
			}catch(SQLException e) {
				return false;
			}
		}
		
		/**
		 * 
		 * @param votazione
		 * @param candidato
		 * @return true se il candidato è stato aggiungo alla votazione,
		 * 		false se era già partecipante o viene sollevata una SQLException.
		 * @throws NullPointerException se <b>votazione</b> o <b>partito</b> sono riferimento a null.
		 */
		public boolean registraPartecipazioneCandidato(VotazioneClassica votazione, Candidato candidato) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(candidato);
			try {
				if (votiCandidati.queryForMatching(new VotiCandidato(votazione, candidato)).size() > 0)
					return false;
				
				votiCandidati.create(new VotiCandidato(votazione, candidato));
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
				if(votiVotazioni.queryForMatching(new Voti(votazione, utente)).size() > 0)
					return false;
				
				votiVotazioni.create(new Voti(votazione, utente));
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
		 * @throws NullPointerExceptionse <b>votazione</b> o <b>partito</b> è riferimento a null.
		 */
		public boolean registraEsitoVotazione(VotazioneClassica votazione, Partito partito) {
			Objects.requireNonNull(votazione);
			Objects.requireNonNull(partito);
			try {
				 // da controllare il funzionamento
				List<VotiPartito> vp = votiPartiti.queryForMatching(new VotiPartito(votazione, partito));
				if(vp.size() != 0) {
					vp.get(0).aggiungiVoto();
					votiPartiti.update(vp.get(0));
					votazione.aggiungiVoto();
					votazioni.update(votazione);
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
		 * @throws NullPointerExceptionse <b>votazione</b> è riferimento a null.
		 */
		public boolean registraEsitoVotazione(VotazioneClassica votazione) {
			Objects.requireNonNull(votazione);
			try {
				votazione.aggiungiBianca();
				votazioni.update(votazione);
				return true;
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
				if(votiReferendum.queryForMatching(new VotiReferendum(referendum, utente)).size() > 0)
					return false;
				
				votiReferendum.create(new VotiReferendum(referendum, utente));
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
		 * ######### Data Retrieve Operations #########
		 */
		
		
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
		
		//public List<>

		/**
		 * TODO
		 * @param utente
		 * @return
		 */
		public List<VotazioneClassica> getVotazioniUtente(Utente utente){
			try {
				List<Voti> votiUtente = votiVotazioni.queryForEq("utente", utente.getCF());
				
				List<VotazioneClassica> v = new ArrayList<>();
				for( int i=0; i<votiUtente.size(); i++) {
					v.add(votazioni.queryForId(votiUtente.get(i).getId()));
				}
				return v;
			}catch(SQLException e) {
				return null;
			}
		}
		
		/**
		 * TODO
		 * @param utente
		 * @return
		 */
		public List<Referendum> getReferendumUtente(Utente utente){
			try {
				List<VotiReferendum> refUtente = votiReferendum.queryForEq("utente", utente.getCF());
				
				List<Referendum> r = new ArrayList<>();
				for( int i=0; i<refUtente.size(); i++) {
					r.add(referendums.queryForId(refUtente.get(i).getId()));
				}
				return r;
			}catch(SQLException e) {
				return null;
			}
		}
		
	 	public void createFakeData() {
        try {
        	
        	System.out.println("CREO UTENTI #######");
        	String pw1 = Hashing.sha256()
        			.hashString("siuii420", StandardCharsets.UTF_8).toString();
            Utente u1 = new Utente("BRTDNL98E27F205P", pw1, "Daniel", "Bartolomei");
            
            String pw2 = Hashing.sha256()
        			.hashString("rennee2022", StandardCharsets.UTF_8).toString();
            Utente u2 = new Utente("RVRSMN98A21F205Z", pw2, "Simone", "Rover");


            utenti.createIfNotExists(u1);
            utenti.createIfNotExists(u2);
            
            System.out.println("FINE UTENTI #######");
            
            System.out.println("CREO VOTAZIONI #######");

            VotazioneClassica vc = new VotazioneClassica("Elezioni", new Date(1662242400000L) 
            		, new Date(1662760800000L), true, TipoVotazione.CATEGORICO);
            
            Referendum r = new Referendum("Energia Nucleare", new Date(1662242400000L) 
            		, new Date(1662760800000L), true);

            votazioni.create(vc);
            referendums.create(r);
            
            System.out.println("FINE VOTAZIONI #######");
            
            System.out.println("CREO PARTITI #######");
            
            Partito p1 = new Partito("Cinque Stelle");
            Partito p2 = new Partito("PD");
            
            partiti.create(p1);
            partiti.create(p2);
            
            System.out.println("FINE PARTITI #######");
            
            System.out.println("CREO CANDIDATI #######");
            
            aggiungiCandidato("Mario", "Rossi", "Cinque Stelle");
            aggiungiCandidato("Berto", "Pertici", "PD");
            aggiungiCandidato("Normanno", "Tiraghi", "PD"); 
            
            System.out.println("FINE CANDIDATI #######");

        	
            System.out.println("CREO PARTECIPAZIONI E VOTI #######");
            
            
            registraPartecipazionePartito(vc, p1);
            registraPartecipazionePartito(vc, p2);
            
            registraVotoReferendum(u1, r);
            registraEsitoReferendum(r, TipoEsitoRef.FAVOREVOLE);
            registraVotoReferendum(u2, r);
            registraEsitoReferendum(r, TipoEsitoRef.BIANCA);
            
            registraVotoVotazione(u1, vc);
            registraEsitoVotazione(vc, p1);
            registraVotoVotazione(u2, vc);
            registraEsitoVotazione(vc, p1);
            
            
            System.out.println("FINE #######");
            
            
            
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
				
				
}
