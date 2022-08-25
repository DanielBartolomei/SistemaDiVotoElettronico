package models;

import com.j256.ormlite.field.DatabaseField;

public class VotiPartito {

	@DatabaseField(foreign = true)
	private VotazioneClassica vc;
	
	@DatabaseField(foreign = true)
	private Partito partito;
	
	@DatabaseField
	private long totVoti;
}
