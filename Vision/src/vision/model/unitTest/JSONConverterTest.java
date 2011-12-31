package vision.model.unitTest;

import java.util.List;

import org.json.HTTP;
import org.json.JSONException;

import vision.model.JSONConverter;
import vision.model.Sensor;

import junit.framework.TestCase;

public class JSONConverterTest extends TestCase {
	public JSONConverter json;

	public void testConvert() {
		json.convert();
		assertNotNull("JSON-Objekt Sollte nicht null sein", json.getJson());
	}
	
	public void testGetUrl() throws JSONException {
		assertNotNull("Link defekt?", HTTP.toJSONObject(json.getUrl()));
	}
	
	public void testAddToSensorList() {
		List<Sensor> sensorList = json.getSensorList();
		json.convert();
		json.addSensorToList();
		assertEquals("Sensoriste vor und nach den Hinzufügen gleich!", json.getSensorList(), sensorList);
	}
	
}
