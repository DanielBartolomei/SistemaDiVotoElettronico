package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiCandidato")
public class VotiCandidato {

	@DatabaseField(foreign = true)
	private VotazioneClassica vc;
	
	@DatabaseField(foreign = true)
	private Candidato candidato;
	
	@DatabaseField
	private long totVoti;
}
