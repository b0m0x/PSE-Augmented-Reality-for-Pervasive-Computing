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

	public JSONConverter() {
		this.sensorList = new ArrayList<Sensor>();
	}

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
			conn.setReadTimeout(500);
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
				sensor.setPosition(new Position(0, 0, 0));
				sensor.setId(JSONObject.getNames(jo)[sensorid]);

				this.addSensorToList();

			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private double calcX(double lon2, double lon1, double lat2, double lat1) {
		return (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
	}

	private double calcY(double lat2, double lat1) {
		return (lat2 - lat1);
	}

	private double calcD(double x, double y, int R) {
		return Math.sqrt(x * x + y * y) * R;
	}

	private double diff(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private double surface(double r1, double r2, double d) {
		return 0.25 * Math.sqrt(Math.abs((d + r1 + r2) * (-d + r1 + r2)
				* (d - r1 + r2) * d + r1 - r2));
	}

	public Position calcLocalPos(String lat, String lon) {
		double latitude = Double.parseDouble(lat);
		double longitude = Double.parseDouble(lon);

		final int R = 6371;

		double ref1lat = 49.013089;
		double ref1lon = 8.4241;

		double ref2lat = 49.013099;
		double ref2lon = 8.424298;

		double ref3lat = 49.012681;
		double ref3lon = 8.424321;

		double x1 = 0;
		double y1 = 0;

		double x2 = 11.83;
		double y2 = 0;

		double x3 = 11.83;
		double y3 = 45.5;

		double d1 = calcD(calcX(ref1lon, longitude, ref1lat, latitude), calcY(
				ref1lat, latitude), R);

		double d2 = calcD(calcX(ref2lon, longitude, ref2lat, latitude), calcY(
				ref2lat, latitude), R);

		double d3 = calcD(calcX(ref3lon, longitude, ref3lat, latitude), calcY(
				ref3lat, latitude), R);

		double d = diff(x1, y1, x2, y2);
		double f = surface(d1, d2, d);

		double intcp1x = 0.5 * (x1 + x2 - ((d1 * d1 - d2 * d2) * (x1 - x2) + 4
				* (y1 - y2) * f)
				/ (d * d));

		double intcp1y = 0.5 * (y1 + y2 - ((d1 * d1 - d2 * d2) * (y1 - y2) - 4
				* (x1 - x2) * f)
				/ (d * d));

		double intcp2x = 0.5 * (x1 + x2 - ((d1 * d1 - d2 * d2) * (x1 - x2) - 4
				* (y1 - y2) * f)
				/ d);

		double intcp2y = 0.5 * (y1 + y2 - ((d1 * d1 - d2 * d2) * (y1 - y2) + 4
				* (x1 - x2) * f)
				/ (d * d));

		double diff1 = diff(intcp1x, intcp1y, x3, y3);
		double diff2 = diff(intcp2x, intcp2y, x3, y3);
		// System.out.println(diff1);
		// System.out.println(diff2);
		// System.out.println(d3);

		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3 + "\n");

		System.out.println(intcp1x);
		System.out.println(intcp1y);

		System.out.println("\n" + intcp2x);
		System.out.println(intcp2y + "\n");

		Position pos;

		if (Math.abs(diff1 - d3) <= 0.01) {
			pos = new Position((float) intcp1x, (float) intcp1y, 0);
		} else if (Math.abs(diff2 - d3) <= 0.1) {
			pos = new Position((float) intcp2x, (float) intcp2y, 0);
		} else {
			System.out.println("Du hast nur müll gerechnet -.-");
			pos = new Position(0, 0, 0);
		}

		return pos;
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
