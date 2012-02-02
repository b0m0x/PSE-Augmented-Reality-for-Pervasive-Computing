package vision.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vision.Config;

public class JSONConverter {

	private static final Logger LOG = Logger.getLogger(JSONConverter.class
			.getName());

	public JSONConverter(Model model) {
		this.sensorList = new ArrayList<Sensor>();
		this.model = model;
	}

	private Model model;
	private JSONObject json;
	private Sensor sensor;
	private List<Sensor> sensorList;

	public String getUrl() {
		return Config.SERVER_URL;
	}

	public JSONObject getJson() {
		return this.json;
	}

	public String offlineStream() {
		File file = new File("offlinestream");
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			return br.readLine();
		} catch (FileNotFoundException e) {
			LOG.warning("File offlinestream not found!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error.";

	}

	public String getJSONStream() {
		try {
			URL url = new URL(getUrl());
			LOG.info("Connecting to " + Config.SERVER_URL + "...");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			LOG.info("Connection successful.");
			conn.setReadTimeout(1500);
			conn.setConnectTimeout(1500);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));

			String content = br.readLine();
			return content;
		} catch (MalformedURLException e) {
			LOG.warning("Connection Error(1). Using offlinestream instead...");
			return offlineStream();
		} catch (IOException e) {
			LOG.warning("Connection Error(2). Using offlinestream instead...");
			return offlineStream();
		}

	}

	public void convert() {
		this.resetList();
		String stream = getJSONStream();
		JSONObject jo;

		try {
			jo = new JSONObject(stream);
			for (int sensorid = 0; sensorid < jo.length(); sensorid++) {

				JSONObject lvl1 = jo
						.getJSONObject(JSONObject.getNames(jo)[sensorid]);
				sensor = new Sensor();

				for (int type = 0; type < jo.getJSONObject(
						JSONObject.getNames(jo)[sensorid])
						.getJSONObject("data").length(); type++) {

					JSONObject lvl2 = lvl1.getJSONObject("data");
					JSONObject temp = jo.getJSONObject(
							JSONObject.getNames(jo)[sensorid]).getJSONObject(
							"data");
					Sample sample = new Sample();

					JSONObject lvl3 = lvl2.getJSONObject(JSONObject
							.getNames(lvl2)[type]);

					// sample.setSensor(sensor);

					sample.setTyp(JSONObject.getNames(lvl2)[type]);
					sample.setUnit(lvl3.getString("unit"));
					sample.setUpdate(Long.parseLong(lvl3.getString("updated")));
					sample.setValue(Float.parseFloat(lvl3.getString("value")));

					sensor.addToSamples(sample);
				}

				JSONArray ja = lvl1.getJSONArray("tags");
				for (int i = 0; i < ja.length(); i++) {
					sensor.addToTags(ja.getString(i));
				}
				sensor.setUpdate(Long.parseLong(lvl1.getString("updated")));
				sensor.setDescription(lvl1.getString("description"));
				
				double lat = lvl1.getDouble("latitude");
				double lon = lvl1.getDouble("longitude");
				sensor.setPosition(getLocalCoordinates(lat, lon));
				sensor.setId(JSONObject.getNames(jo)[sensorid]);

				this.addSensorToList();

			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	private Position getLocalCoordinates(double lat, double lon) {
		if (model.getReferencePoints().size() < 2) {
			throw new UnsupportedOperationException("Need at least 2 Reference Points");
		}
		Reference ref1 = model.getReferencePoints().get(0);
		Reference ref2 = model.getReferencePoints().get(1);
		
		double dLat = ref2.lat - ref1.lat;
		double dLon = ref2.lon - ref1.lon;
		
//		double dLat = 49.01308663519554f - 49.01265562319531f;
//		double dLon = 8.424110412597656f - 8.424016535282135f;
//		
		double dX = ref2.x - ref1.x;
		double dY = ref2.y - ref1.y;

		float x = (float) (((float)lat - ref1.lat) * dX / dLat + ((float)lon - ref1.lon) * dX / dLon);
//		float y = -(float) (((float)lat - ref1.lat) * dY / dLat + ((float)lon - ref1.lon) * dY / dLon);
		
		//double x = (float) (lon - ref1.lon) * dX / dLon;
		double y = (lat - ref1.lat) * dY / dLat + (lon * dY - ref1.lon * dY) / dLon;
		
//		float x = (float) ((lat - 49.01308663519554f) * dX / dLat + (lon - 8.424110412597656f) * dX / dLon);
//		float y = (float) ((lat - 49.01308663519554f) * dY / dLat + (lon - 8.424110412597656f) * dY / dLon);
		
		return new Position((float) x, 0, (float) y);
	}

	public void resetList() {
		this.sensorList.clear();
	}

	public void addSensorToList() {
		this.sensorList.add(this.sensor);
	}

	public List<Sensor> getSensorList() {
		return this.sensorList;
	}

}
