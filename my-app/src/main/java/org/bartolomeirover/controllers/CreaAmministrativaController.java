package org.bartolomeirover.controllers;

import java.time.LocalDate;

import org.bartolomeirover.common.Controller;

public class CreaAmministrativaController extends Controller {

	private String nomeVotazione, tipoAmministrative;
	private LocalDate dataInizio, dataFine;
	private boolean isAssoluta;
	
	public void sendData(String nomeVotazione, LocalDate dataInizio, LocalDate dataFine, boolean isAssoluta, 
			String tipoAmministrative) {
		this.nomeVotazione = nomeVotazione;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.isAssoluta = isAssoluta;
		this.tipoAmministrative = tipoAmministrative;
		System.out.println("DATA RECEIVED!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}
