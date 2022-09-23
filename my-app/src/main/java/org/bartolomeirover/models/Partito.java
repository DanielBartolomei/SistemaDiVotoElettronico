package org.bartolomeirover.models;




import java.util.ArrayList;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "partito")
public class Partito {
	
	//@invariant nome!=null && candidati.size > 0;
	
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
	
	//@requires candidato!=null
	//@ensures \exists int i; 0<=i<candidati.size; candidati.get(i)==candidato;
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
