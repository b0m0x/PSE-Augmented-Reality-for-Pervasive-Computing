package vision.model;

import java.util.List;

/**
 * manages the database connection and saves sensordata
 *
 * 
 */
public class Database {


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
