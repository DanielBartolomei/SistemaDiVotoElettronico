package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "votiReferendum")
public class VotiReferendum {
	
	@DatabaseField(foreign = true)
	private Referendum referendum;
	
	@DatabaseField(foreign = true)
	private Utente utente;
}
