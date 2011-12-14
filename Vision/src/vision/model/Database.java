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
	
	public List<Sample> getAllSensorData(int id) {
		throw new UnsupportedOperationException();	
	}

}
