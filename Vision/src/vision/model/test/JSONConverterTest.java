package vision.model.test;

import java.util.ArrayList;
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

//	public void testGetUrl() throws JSONException {
//		json = new JSONConverter();
//
//
//		System.out.println(json.getJSONStream());
//	}


//	public void testConvert() {
//		json = new JSONConverter();
//		try {
//			JSONObject jo = new JSONObject(json.getJSONStream());
//
//			System.out.println("Converter:\n");
//			json.convert();
//			List<Sensor> sensors = json.getSensorList();
//
//			for (int i = 0; i < sensors.size(); i++) {
//				System.out.println("ID: " + sensors.get(i).getId());
//				System.out.println("Last updated: "
//						+ sensors.get(i).getUpdate());
//				System.out.println("Description: "
//						+ sensors.get(i).getDescription());
//				System.out.println("Tags:" ); 
//					for(int j = 0; j < sensors.get(i).getTags().size(); j++) {
//						System.out.print(sensors.get(i).getTags().get(j));
//						if(j != sensors.get(i).getTags().size() - 1) {
//							System.out.print(",");
//						} else {
//							System.out.println("\n");
//						}
//					}
//				System.out.println("Position: "
//						+ sensors.get(i).getPosition().toString() + "\n");
//				System.out.println("Data: " + "\n");
//				for (int j = 0; j < sensors.get(i).getMesswert().size(); j++) {
//					System.out.println("Type: "
//							+ sensors.get(i).getMesswert().get(j).getTyp());
//					System.out.println("Unit: "
//							+ sensors.get(i).getMesswert().get(j).getUnit());
//					System.out.println("Last Updated: "
//							+ sensors.get(i).getMesswert().get(j).getUpdate());
//					System.out.println("Value : "
//							+ sensors.get(i).getMesswert().get(j).getValue()
//							+ "\n");
//				}
//
//				System.out
//						.println("-----------------------------------------------------------------------------------------------------");
//
//			}
//
//		} catch (JSONException e) {
//			System.out.println("Error!");
//		}
//	}
//
	
//	public void testOfflineStream() {
//		json = new JSONConverter();
//		System.out.println(json.offlineStream());
//	}
	
}
