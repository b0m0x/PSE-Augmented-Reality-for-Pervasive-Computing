package vision.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {

	private JSONObject json;
	private Sensor sensor;
	private List<Sensor> sensorList;

	public String getUrl() {
		return "";
	}
	
	public JSONObject getJson() {
		return this.json;
	}

	public void convert() {
		try {
			json = HTTP.toJSONObject(getUrl());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sensor = new Sensor();
		try {
			
			sensor.setDescription(json.getString("Declassscription"));
			sensor.setDescription(json.getString("Description"));
			sensor.setId(json.getString("ID"));
			sensor.setMesswert(new Sample()); //create sample method
			sensor.setPosition(new Position()); //create position method
			sensor.setRegistered(true); //create registered method
			sensor.setTags(null); //create tags method
			sensor.setUpdate(Integer.parseInt(json.getString("Update")));
			
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.addSensorToList();
		}

	}
	
	private Sample makeSample(String s) {
		return null;
	}
	
	private Position makePosition(String s) {
		return null;
	}
	
	private boolean makeRegistered(String s) {
		return false;
	}
	
	private List<String> makeTags(String s) {
		return null;
	}

	public void addSensorToList() {
		this.sensorList.add(this.sensor);
	}

	public List<Sensor> getSensorList() {
		return this.sensorList;
	}

}
