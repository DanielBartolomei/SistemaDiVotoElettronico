package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "voti")
public class Voti {
	
	@DatabaseField(foreign = true)
	private VotazioneClassica vc;
	
	@DatabaseField(foreign = true)
	private Utente utente;

}
