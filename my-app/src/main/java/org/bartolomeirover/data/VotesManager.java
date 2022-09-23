package org.bartolomeirover.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bartolomeirover.common.DateUtils;
import org.bartolomeirover.models.*;

public class VotesManager {

	public VotesManager() {
		
	}
	
	/**
	 * Data la lista di oggetti <b>VotiPartito</b> calcola i vincitori della votazione.
	 * @param voti - La lista di <b>VotiCandidato</b>
	 * @param tipo - Il tipo della votazione
	 * @return La mappa contenente le associazioni tra un <b>Partito</b> vincitore e il numero di voti ricevuti.
	 */
	public static Map<Partito, Integer> getVincitorePartito(List<VotiPartito> voti, TipoVotazione tipo){
		Objects.requireNonNull(voti);
		Objects.requireNonNull(tipo);
		
		long min = voti.get(voti.size()-1).getVoti();
		long max = voti.get(0).getVoti();
		Map<Partito, Integer> vincitori = new HashMap<>();
		
		switch(tipo){
			
			case CATEGORICO_PARTITI:
			case CON_PREFERENZA:	
				for(int i=0; i<voti.size(); i++) {
					if(voti.get(i).getVoti() < max ) break;
					vincitori.put(voti.get(i).getPartito(), Math.toIntExact(voti.get(i).getVoti()));
				}
				break;
				
			case ORDINALE_PARTITI:
				for(int i=voti.size()-1; i>=0; i--) {
					if(voti.get(i).getVoti() > min ) break;
					vincitori.put(voti.get(i).getPartito(), Math.toIntExact(voti.get(i).getVoti()));
				}
				break;
			
			default:
		}
		return vincitori;
		
	}
	
	/**
	 * Data la lista di oggetti <b>VotiCandidato</b> calcola i vincitori della votazione.
	 * @param voti - La lista di <b>VotiCandidato</b>
	 * @param tipo - Il tipo della votazione
	 * @return La mappa contenente le associazioni tra un <b>Candidato</b> vincitore e il numero di voti ricevuti.
	 */
	public static Map<Candidato, Integer> getVincitoreCandidato(List<VotiCandidato> voti, TipoVotazione tipo){
		Objects.requireNonNull(voti);
		Objects.requireNonNull(tipo);
		
		long min = voti.get(voti.size()-1).getVoti();
		long max = voti.get(0).getVoti();
		Map<Candidato, Integer> vincitori = new HashMap<>();
		
		switch(tipo){
			
			case CATEGORICO_CANDIDATI:
				for(int i=voti.size()-1; i>=0; i--) {
					if(voti.get(i).getVoti() < max ) break;
					vincitori.put(voti.get(i).getCandidato(), Math.toIntExact(voti.get(i).getVoti()));
				}
				break;
				
			case ORDINALE_CANDIDATI:
				for(int i=0; i<voti.size(); i++) {
					if(voti.get(i).getVoti() > min ) break;
					vincitori.put(voti.get(i).getCandidato(), Math.toIntExact(voti.get(i).getVoti()));
				}
				break;
			
			default:
		}
		
		return vincitori;
		
	}
	
	/**
	 * Verifica i candidati di un partito che hanno ricevuto più preferenze.
	 * @param vc - La lista di <b>VotiCandidato</b> 
	 * @param p - Il partito di cui verificare i voti dei candidati
	 * @return La lista dei candidati del partito <i>p</i> con più voti.
	 */
	public static List<Candidato> getPreferenze(List<VotiCandidato> vc, Partito p){
		
		for(int i=0; i < vc.size(); i++) {
			if(!vc.get(i).getCandidato().getPartito().getNome().equals(p.getNome())) {
				vc.remove(i);
			}
		}
		
		if(vc.size() == 0) return new ArrayList<>();
		
		long max = vc.get(0).getVoti();
		
		List<Candidato> preferenze = new ArrayList<>();
		for(int i=0; i < vc.size() ; i++) {
			if(vc.get(i).getVoti() < max) break;
			preferenze.add(vc.get(i).getCandidato());
		}
		
		return preferenze;
	}

	/**
	 * Verifica la maggioranza assoluta.
	 * @param v - Una votazione
	 * @param voti - Il numero di voti da confrontare
	 * @return true se <b>voti</b> è superiore o uguale alla maggioranza assoluta per la votazione <b>v</b>, false altrimenti.
	 */
	public static boolean hasMaggioranzaAssoluta(VotazioneClassica v, int voti) {
		return voti >= (v.getVotiTotali() / 2) + 1;
	}
	
	/**
	 * Dato un referendum, ne ristituisce l'esito.
	 * @param r - Il referendum
	 * @return L'esito del referendum, calcolato secondo gli adeguati parametri o <i>null</i> se la votazione è in corso.
	 */
	public static String getEsitoReferendum(Referendum r) {
		
		if(!DateUtils.hasEnded(r.getFine())){
			return null;
		}
		
		if(r.hasQuorum()) {
			long votanti = r.getVotiTotali();
			if(r.getFavorevoli() >= (votanti / 2) +1) return "SI";
			if(r.getContrari() >= (votanti / 2) + 1) return "NO";
			return "Quorum dei votanti non raggiunto.";
		} else {
			long validi = r.getFavorevoli() + r.getContrari();
			if(r.getFavorevoli() >= (validi / 2) +1) return "SI";
			if(r.getContrari() >= (validi / 2) + 1) return "NO";
			return "Quorum dei voti espressi non raggiunto.";
		}
		
	}
	
	
}
