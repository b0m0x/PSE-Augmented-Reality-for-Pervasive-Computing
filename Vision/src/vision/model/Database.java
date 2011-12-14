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

}
