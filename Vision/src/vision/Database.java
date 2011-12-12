package vision;

import java.util.List;

/**
 * 
 * Manages the Database
 * 
 */
public class Database {

	/**
	 * @uml.property   name="daten"
	 * @uml.associationEnd   inverse="datenbank:vision.Model"
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
		 * writes the data to the database
		 * 
		 * @param id id of the Sensor to update
		 * @param time current time
		 * @param value new value of Sensor
		 */
		public void updateSensors(String id, int time, Sample value){
		}

			
			/**
			 * 
			 * gets all the Sensordata with the id and time
			 * 
			 * @param id id for the Sample
			 * @param time time of data
			 * @return returns list of Samples with given id and time
			 */
			public List<Sample> getSensordata(int id, int time){
				throw new UnsupportedOperationException();
			}

}
