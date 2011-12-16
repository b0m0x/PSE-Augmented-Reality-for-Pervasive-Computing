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
	 * returns all stored samples of a sensor
	 * @param id id of the sensor
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getAllSensorData(int id) {
		throw new UnsupportedOperationException();	
	}
	
	/**
	 * returns all samples of a sensor inbetween a given interval
	 * @param id id of the sensor
	 * @param from timestamp of beginning 
	 * @param to timestamp of the end of the interval
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getSensorDataInterval(int id, long from, long to) {
		throw new UnsupportedOperationException();	
	}

}
