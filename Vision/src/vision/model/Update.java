package vision.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * manages the server connection and fetches the sensor data
 * 
 */
public class Update {
	private static final Logger LOG = Logger.getLogger(Update.class.getName());
	public Update(Model model) {
		this.database = new Database();
		this.jsonConverter = new JSONConverter();
		this.daten = model;
	}

	public Update() {
		this.database = new Database();
		this.jsonConverter = new JSONConverter();
	}

	/**
	 * @uml.property name="daten"
	 * @uml.associationEnd inverse="update:vision.model.Model"
	 */
	/**
	 * provides a facade for all objects belonging to the model
	 * 
	 */

	private Model daten;

	/**
	 * Getter of the property <tt>daten</tt>
	 * 
	 * @return Returns the daten.arg0
	 * @uml.property name="daten"
	 */
	public Model getDaten() {
		return daten;
	}

	/**
	 * Setter of the property <tt>daten</tt>
	 * 
	 * @param daten
	 *            The daten to set.
	 * @uml.property name="daten"
	 */
	public void setDaten(Model daten) {
		this.daten = daten;
	}

	/**
	 * @uml.property name="jSONConverter"
	 * @uml.associationEnd inverse="update:vision.model.JSONConverter"
	 */
	private JSONConverter jsonConverter;

	/**
	 * Getter of the property <tt>jSONConverter</tt>
	 * 
	 * @return Returns the jsonConverter.
	 * @uml.property name="jSONConverter"
	 */
	public JSONConverter getJSONConverter() {
		return jsonConverter;
	}

	/**
	 * Setter of the property <tt>jSONConverter</tt> arg0
	 * 
	 * @param jSONConverter
	 *            The jsonConverter to set.
	 * @uml.property name="jSONConverter"
	 */
	public void setJSONConverter(JSONConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	/**
	 * @uml.property name="database"
	 * @uml.associationEnd inverse="update:vision.model.Database"
	 */
	private Database database;

	/**
	 * Getter of the property <tt>database</tt>
	 * 
	 * @return Returns the database.
	 * @uml.property name="database"
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * Setter of the property <tt>database</tt>
	 * 
	 * @param database
	 *            The database to set.
	 * @uml.property name="database"
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}

	/**
	 * stores all the Data from the JSON-Stream into the database
	 * 
	 * @param l
	 *            the current time of when the data was stored
	 */
	public void store(long l) {

		jsonConverter.convert();
		List<Sensor> sensorlist = new ArrayList<Sensor>();
		sensorlist = jsonConverter.getSensorList();

		// daten.setSensor(sensorlist);
		int samplecounter = 1;

		for (int i = 0; i < sensorlist.size(); i++) {
			for (int j = 0; j < sensorlist.get(i).getMesswert().size(); j++) {
				database.updateSensors(sensorlist.get(i).getId(), l, sensorlist
						.get(i).getMesswert().get(j), sensorlist.get(i)
						.getTags());
				LOG.info("Sample " + (j + 1));
				samplecounter++;
			}
		}
		LOG.info("Sensors stored: " + (sensorlist.size() + 1)
				+ "; Samples stored: " + samplecounter);
		jsonConverter.resetList();
	}

	/**
		 */
	public void getAllData() {
		jsonConverter.convert();
	}

}