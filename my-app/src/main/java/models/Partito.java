package models;

import java.util.List;
import java.util.ArrayList;

public class Partito {
	private String nome;
	private List<Candidato> lista;
	
	public Partito(String nome) {
		this.nome = nome;
		this.lista = new ArrayList<Candidato>();
	}
	
	public Partito(String nome, ArrayList<Candidato> lista) {
		this.nome = nome;
		this.lista = lista;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public Candidato getCandidatoAt(int index) {
		return lista.get(index);
	}
	
	public int getListaLength() {
		return lista.size();
	}
}
