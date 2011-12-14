package vision.model;

import java.util.List;

import org.json.JSONObject;


public class JSONConverter {

	private JSONObject json;
	private Sensor sensor;
	private List<Sensor> sensorList;
	
	public void getUrl() {
		
	}
	
	public void convert() {
		
	}
	
	public void saveSensor() {
		
	}
	
	public void addSensorToList() {
		
	}
	
	public List<Sensor> getSensorList() {
		return this.sensorList;
	}

	/**
	 * @uml.property  name="update"
	 * @uml.associationEnd  inverse="jSONConverter:vision.model.Update"
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
