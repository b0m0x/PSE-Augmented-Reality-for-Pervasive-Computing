package vision.model;


public class Update {

	/**
	 * @uml.property  name="url"
	 */
	private String url;

	/**
	 * Getter of the property <tt>url</tt>
	 * @return  Returns the url.
	 * @uml.property  name="url"
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Setter of the property <tt>url</tt>
	 * @param url  The url to set.
	 * @uml.property  name="url"
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @uml.property   name="daten"
	 * @uml.associationEnd   inverse="update:vision.model.Model"
	 */
	private Model daten;

	/**
	 * Getter of the property <tt>daten</tt>
	 * @return  Returns the daten.
	 * @uml.property  name="daten"
	 */
	public Model getDaten() {
		return daten;
	}

	/**
	 * Setter of the property <tt>daten</tt>
	 * @param daten  The daten to set.
	 * @uml.property  name="daten"
	 */
	public void setDaten(Model daten) {
		this.daten = daten;
	}

	/**
	 * @uml.property  name="jSONConverter"
	 * @uml.associationEnd  inverse="update:vision.model.JSONConverter"
	 */
	private JSONConverter jsonConverter;

	/**
	 * Getter of the property <tt>jSONConverter</tt>
	 * @return  Returns the jsonConverter.
	 * @uml.property  name="jSONConverter"
	 */
	public JSONConverter getJSONConverter() {
		return jsonConverter;
	}

	/**
	 * Setter of the property <tt>jSONConverter</tt>
	 * @param jSONConverter  The jsonConverter to set.
	 * @uml.property  name="jSONConverter"
	 */
	public void setJSONConverter(JSONConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	/**
	 * @uml.property  name="database"
	 * @uml.associationEnd  inverse="update:vision.model.Database"
	 */
	private Database database;

	/**
	 * Getter of the property <tt>database</tt>
	 * @return  Returns the database.
	 * @uml.property  name="database"
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * Setter of the property <tt>database</tt>
	 * @param database  The database to set.
	 * @uml.property  name="database"
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}
	
	public void store() {
		
	}

}
