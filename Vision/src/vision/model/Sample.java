package vision.model;


public class Sample {

	/**
	 * @uml.property  name="typ"
	 */
	private String typ;

	/**
	 * Getter of the property <tt>typ</tt>
	 * @return  Returns the typ.
	 * @uml.property  name="typ"
	 */
	public String getTyp() {
		return typ;
	}

	/**
	 * Setter of the property <tt>typ</tt>
	 * @param typ  The typ to set.
	 * @uml.property  name="typ"
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * @uml.property  name="unit"
	 */
	private String unit;

	/**
	 * Getter of the property <tt>unit</tt>
	 * @return  Returns the unit.
	 * @uml.property  name="unit"
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Setter of the property <tt>unit</tt>
	 * @param unit  The unit to set.
	 * @uml.property  name="unit"
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @uml.property  name="value"
	 */
	private float value;

	/**
	 * Getter of the property <tt>value</tt>
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public float getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * @param value  The value to set.
	 * @uml.property  name="value"
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * @uml.property  name="update"
	 */
	private int update;

	/**
	 * Getter of the property <tt>update</tt>
	 * @return  Returns the update.
	 * @uml.property  name="update"
	 */
	public int getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * @param update  The update to set.
	 * @uml.property  name="update"
	 */
	public void setUpdate(int update) {
		this.update = update;
	}

		
		/**
		 */
		public void getMesswert(){
		}

			
			/**
			 */
			public void setMesswert(){
			}

			/**
			 * @uml.property   name="sensor"
			 * @uml.associationEnd   inverse="messwert:vision.model.Sensor"
			 */
			private Sensor sensor;

			/**
			 * Getter of the property <tt>sensor</tt>
			 * @return  Returns the sensor.
			 * @uml.property  name="sensor"
			 */
			public Sensor getSensor() {
				return sensor;
			}

			/**
			 * Setter of the property <tt>sensor</tt>
			 * @param sensor  The sensor to set.
			 * @uml.property  name="sensor"
			 */
			public void setSensor(Sensor sensor) {
				this.sensor = sensor;
			}

}
