package models;

import java.util.List;
import java.util.ArrayList;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "partito")
public class Partito {
	
	@DatabaseField
	private String nome;
	
	public String getNome() {
		return this.nome;
	}
	
	@ForeignCollectionField
	ForeignCollection<Candidato> candidati;
	
	public ForeignCollection<Candidato> getCandidati(){
		return candidati;
	}
	
	public Partito() {

	}
}
