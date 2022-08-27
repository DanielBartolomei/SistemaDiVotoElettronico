package models;

import java.util.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiPartito")
public class VotiPartito {
	
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private VotazioneClassica vc;
	
	@DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Partito partito;
	
	@DatabaseField
	private long totVoti;
	
	
	/**
	 *  Empty Constructor
	 */
	
	public VotiPartito() {
		
	}
	
	/**
	 *  Other Methods
	 */
	
	public void addVote() {
		totVoti += 1;
	}
	
}
