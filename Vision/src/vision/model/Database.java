package vision.model;

import java.util.List;

/**
 * manages the database connection and saves sensordata
 *
 * 
 */
public class Database {

	/**
	 * @uml.property name="daten"
	 * @uml.associationEnd inverse="datenbank:vision.model.Model"
	 */
	private Model daten;

	/**
	 * Getter of the property <tt>daten</tt>
	 * 
	 * @return Returns the daten.
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
	 * saves a sensor object in the database
		 */
	public void updateSensors(String id, int zeitpunkt, Sample messwerte) {
	}

	/**
	 * fetches the sensor samples collected 
	*/
	public List<Sample> getSensordata(int id, int zeitpunkt) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param id id of the sensor
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getAllSensorData(int id) {
		throw new UnsupportedOperationException();	
	}

	/**
	 * @uml.property  name="update"
	 * @uml.associationEnd  inverse="database:vision.model.Update"
	 */
	private Update update;

	/**
	 * Getter of the property <tt>update</tt>
	 * @return  Returns the update.
	 * @uml.property  name="update"
	 */
	public Update getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * @param update  The update to set.
	 * @uml.property  name="update"
	 */
	public void setUpdate(Update update) {
		this.update = update;
	}

}
