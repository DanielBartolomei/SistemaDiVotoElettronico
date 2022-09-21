package org.bartolomeirover.models;




import java.util.ArrayList;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "partito")
public class Partito {
	
	@DatabaseField(id = true)
	private String nome;
	
	public String getNome() {
		return this.nome;
	}
	
	@ForeignCollectionField
	ForeignCollection<Candidato> candidati;
	
	public ArrayList<Candidato> getCandidati(){
		return new ArrayList<>(candidati);
	}
	
	public void aggiungiCandidato(Candidato candidato) {
		candidati.add(candidato);
	}
	
	public Partito() {
		
	}
	
	public Partito(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
