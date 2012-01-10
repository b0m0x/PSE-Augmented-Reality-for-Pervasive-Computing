package vision.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import vision.Config;

public class JSONConverter {

	private JSONObject json;
	private Sensor sensor;
	private List<Sensor> sensorList;

	public String getUrl() {
		return Config.serverUrl;
	}

	public JSONObject getJson() {
		return this.json;
	}

	public String getJSONStream() {
		try {
			URL url = new URL(getUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));

			String content = br.readLine();
			return content;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// public String[] splitStream() {
	// String s = getJSONStream();
	// String[] array = new String[100];
	// int mem = 0;
	// int arraymem = 0;
	// for (int i = 0; i < s.length() - 4; i++) {
	// if (s.charAt(i) == '"' && s.charAt(i + 1) == '1'
	// && s.charAt(i + 2) == '.' && s.charAt(i + 3) == '2') {
	// String temp = s.substring(mem, s.charAt(i - 1));
	// mem = s.charAt(i - 1);
	// array[arraymem] = temp;
	// arraymem++;
	// }
	//
	// }
	// return array;
	// }

	public void convert() {
		String stream = getJSONStream();
		JSONObject jo;
		this.sensorList = new ArrayList<Sensor>();
		try {
			jo = new JSONObject(stream);
			for (int sensorid = 0; sensorid < jo.length(); sensorid++) {

//				if (!jo.getNames(jo)[sensorid].equals("tester")
//						&& !jo.getNames(jo)[sensorid].equals("Heater")) {

					JSONObject lvl1 = jo
							.getJSONObject(jo.getNames(jo)[sensorid]);
					sensor = new Sensor();

					for (int type = 0; type < jo.getJSONObject(
							jo.getNames(jo)[sensorid]).getJSONObject("data")
							.length(); type++) {

						JSONObject lvl2 = lvl1.getJSONObject("data");
						JSONObject temp = jo.getJSONObject(
								jo.getNames(jo)[sensorid])
								.getJSONObject("data");
						Sample sample = new Sample();

						JSONObject lvl3 = lvl2.getJSONObject(lvl2
								.getNames(lvl2)[type]);

						if (sample.getSensor() == null) {
							sample.setSensor(sensor);
						}

						sample.setTyp(lvl2.getNames(lvl2)[type]);
						sample.setUnit(lvl3.getString("unit"));
						sample.setUpdate(Long.parseLong(lvl3
								.getString("updated")));
						sample.setValue(Float.parseFloat(lvl3
								.getString("value")));

						sensor.addToList(sample);
					}

					sensor.setUpdate(Long.parseLong(lvl1.getString("updated")));
					sensor.setDescription(lvl1.getString("description"));
					sensor.setPosition(new Position(0, 0, 0));
					sensor.setId(jo.getNames(jo)[sensorid]);
					this.addSensorToList();

					System.out.println("ID: " + sensor.getId());
					System.out.println("Last updated: " + sensor.getUpdate());
					System.out.println("Description: "
							+ sensor.getDescription());
					System.out.println("Position: "
							+ sensor.getPosition().toString() + "\n");
					System.out.println("Data: " + "\n");
					for (int i = 0; i < sensor.getMesswert().size(); i++) {
						System.out.println("Type: "
								+ sensor.getMesswert().get(i).getTyp());
						System.out.println("Unit: "
								+ sensor.getMesswert().get(i).getUnit());
						System.out.println("Last Updated: "
								+ sensor.getMesswert().get(i).getUpdate());
						System.out
								.println("Value : "
										+ sensor.getMesswert().get(i)
												.getValue() + "\n");
					}

					System.out
							.println("-----------------------------------------------------------------------------------------------------");
				}
			//}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
