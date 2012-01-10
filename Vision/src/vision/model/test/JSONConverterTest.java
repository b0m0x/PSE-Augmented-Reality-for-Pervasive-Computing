package vision.model.test;

import java.util.List;

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import vision.model.JSONConverter;
import vision.model.Sensor;

import junit.framework.TestCase;

public class JSONConverterTest extends TestCase {
	public JSONConverter json;

	// public void testConvert() {
	// json = new JSONConverter();
	//		
	// json.convert();
	// System.out.println(json.toString());
	// assertNotNull("JSON-Objekt Sollte nicht null sein", json.getJson());
	// }

	public void testGetUrl() throws JSONException {
		json = new JSONConverter();

		// for (int i = 0; i < 10; i++) {
		// System.out.print(json.getJSONStream(i));
		// }
		System.out.println(json.getJSONStream());
	}

	// public void testSplitStream() {
	// json = new JSONConverter();
	// for(int i = 0; i < json.splitStream().length; i++) {
	// System.out.println(json.splitStream()[i]);
	// }
	// }

	public void testConvert() {
		json = new JSONConverter();
		try {
			JSONObject jo = new JSONObject(json.getJSONStream());
			System.out.println(jo.length());
			JSONObject test = new JSONObject(json.getJSONStream().substring(0,
					420)
					+ "}");
			System.out.println(test);
			System.out.println(test.length());
			
			System.out.println("Convert:\n");
			json.convert();

		} catch (JSONException e) {
			System.out.println("Error!");
		}
	}
	// public void testAddToSensorList() {
	// List<Sensor> sensorList = json.getSensorList();
	// json.convert();
	// json.addSensorToList();
	// assertEquals("Sensoriste vor und nach den Hinzufügen gleich!",
	// json.getSensorList(), sensorList);
	// }

}
