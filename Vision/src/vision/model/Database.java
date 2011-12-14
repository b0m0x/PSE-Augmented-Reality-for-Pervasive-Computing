package vision.model;


public class Database {

	/**
	 * @uml.property   name="daten"
	 * @uml.associationEnd   inverse="datenbank:vision.model.Model"
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
		 */
		public void updateSensors(String id, int zeitpunkt, Sample messwerte){
		}

			
			/**
			 */
			public void getSensordata(int id, int zeitpunkt){
			}

}
