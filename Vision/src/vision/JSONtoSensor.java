package vision;

public class JSONtoSensor {

	/**
	 * @uml.property  name="sensor"
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

	/**
	 * @uml.property  name="jsonobject"
	 */
	private JSONObject jsonobject;

	/**
	 * Getter of the property <tt>jsonobject</tt>
	 * @return  Returns the jsonobject.
	 * @uml.property  name="jsonobject"
	 */
	public JSONObject getJsonobject() {
		return jsonobject;
	}

	/**
	 * Setter of the property <tt>jsonobject</tt>
	 * @param jsonobject  The jsonobject to set.
	 * @uml.property  name="jsonobject"
	 */
	public void setJsonobject(JSONObject jsonobject) {
		this.jsonobject = jsonobject;
	}

}
