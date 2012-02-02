package vision.model.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vision.model.Database;
import vision.model.JSONConverter;
import vision.model.Position;
import vision.model.Sample;
import vision.model.Update;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {

	Database db = new Database();

	// public void testCurrentTime() {
	// System.out.println(System.currentTimeMillis());
	// }

	// public void testUpdate() {
	// Update update = new Update();
	// update.getDatabase().connect();
	// update.store(30);
	// update.getDatabase().disconnect();
	// }

//	public void testCalc() {
//		JSONConverter json = new JSONConverter();
//		Position pos = json.calcLocalPos("49.013099", "8.424298");
//		System.out.println("Position: (" + pos.getX() + "/" + pos.getY() + ")");
//	}

	// public void testGetSensorData() {
	// Update update = new Update();
	// update.getDatabase().connect();
	// List<Sample> samples = new ArrayList<Sample>();
	// for (int i = 0; i < update.getDatabase().size(); i++) {
	// String id = update.getDatabase().getIDs(i);
	// System.out.println("ID: " + id);
	// System.out.println("Database ID: " + i + "\n");
	// System.out.println("Tags: ");
	// for (int k = 0; k < update.getDatabase().getTags(i).size(); k++) {
	// System.out.print(update.getDatabase().getTags(i).get(k));
	// if (k != update.getDatabase().getTags(i).size() - 1) {
	// System.out.print(", ");
	// } else {
	// System.out.println();
	// }
	// }
	// samples = update.getDatabase().getSensordata(id, 30);
	// for (int j = 0; j < samples.size(); j++) {
	// Sample sample = samples.get(j);
	// System.out.println("Sample: " + j);
	// System.out.println("Type: " + sample.getTyp());
	// System.out.println("Unit: " + sample.getUnit());
	// System.out.println("Last updated: " + sample.getUpdate());
	// System.out.println("Value: " + sample.getValue() + "\n");
	// }
	// System.out
	// .println("----------------------------------------------------------------------------");
	// }
	//
	// System.out.println("IDs in Database: " + update.getDatabase().size());
	//
	// update.getDatabase().disconnect();
	// }

	// public void testGetSensorData() {
	// Update update = new Update();
	// update.getDatabase().connect();
	// update.getAllData();
	// List<Sample> samples = new ArrayList<Sample>();
	// for (int i = 0; i < update.getJSONConverter().getSensorList().size();
	// i++) {
	// Sensor sensor = update.getJSONConverter().getSensorList().get(i);
	// System.out.println("ID: "
	// + sensor.getId());
	// System.out.println("Database ID: " + i + "\n");
	// samples = update.getDatabase().getSensordata(
	// sensor.getId(), 30);
	// for (int j = 0; j < samples.size(); j++) {
	// Sample sample = samples.get(j);
	// System.out.println("Sample: " + j);
	// System.out.println("Type: " + sample.getTyp());
	// System.out.println("Unit: " + sample.getUnit());
	// System.out.println("Last updated: " + sample.getUpdate());
	// System.out.println("Value: " + sample.getValue() + "\n");
	// }
	// System.out.println("----------------------------------------------------------------------------");
	// }
	//
	// update.getDatabase().disconnect();
	// }

	// public void testGetAllSensorData() {
	// Update update = new Update();
	// update.getDatabase().connect();
	// update.getAllData();
	// List<Sample> samples = new ArrayList<Sample>();
	// for (int i = 0; i < update.getDatabase().size(); i++) {
	// System.out.println("ID: " + update.getDatabase().getIDs(i));
	// System.out.println("Database ID: " + i);
	// samples = update.getDatabase().getAllSensorData(
	// update.getDatabase().getIDs(i));
	// for (int j = 0; j < samples.size(); j++) {
	// Sample sample = samples.get(j);
	// System.out.println("Sample: " + j);
	// System.out.println("Type: " + sample.getTyp());
	// System.out.println("Unit: " + sample.getUnit());
	// System.out.println("Last updated: " + sample.getUpdate());
	// System.out.println("Value: " + sample.getValue() + "\n");
	// }
	// }
	//
	// update.getDatabase().disconnect();
	//
	// }

	// public void testGetAllSensorDataInterval() {
	// Update update = new Update();
	// update.getDatabase().connect();
	// update.getAllData();
	// List<Sample> samples = new ArrayList<Sample>();
	// for (int i = 0; i < update.getJSONConverter().getSensorList().size();
	// i++) {
	// System.out.println("ID: "
	// + update.getJSONConverter().getSensorList().get(i).getId());
	// System.out.println("Database ID: " + i);
	// samples = update.getDatabase().getSensorDataInterval(
	// update.getJSONConverter().getSensorList().get(i).getId(), 30, 32);
	// for (int j = 0; j < samples.size(); j++) {
	// Sample sample = samples.get(j);
	// System.out.println("Sample: " + j);
	// System.out.println("Type: " + sample.getTyp());
	// System.out.println("Unit: " + sample.getUnit());
	// System.out.println("Last updated: " + sample.getUpdate());
	// System.out.println("Value: " + sample.getValue() + "\n");
	// }
	// }
	//
	// update.getDatabase().disconnect();
	//
	// }

	// public void testGetAllSensorData() {
	// assertNotNull(db.getAllSensorData(0));
	// }
	//	
	// public void testGetSensorDataInterval() {
	// assertNotNull(db.getSensorDataInterval(0, 0, 0));
	// }
	
	@Test
	public void testStoreAndFetch() {
		List<String> tags = new ArrayList<String>();
		long time = System.currentTimeMillis() / 1000;
		tags.add("tag1");
		tags.add("tag2");
		
		Database db = new Database();
		db.connect();
		db.updateSensors("test" + time, time, new Sample("Temperatur", "celsious", 22.0f, time), tags);
		db.updateSensors("test" + time, time, new Sample("Fenster", "boolean", 1.0f, time), tags);
		
		List<Sample> fetched = db.getAllSensorData("test" + time);
		assertEquals(2, fetched.size());
		assertEquals(time, fetched.get(0).getUpdate());
		assertEquals("Temperatur", fetched.get(0).getTyp());
		assertEquals("Fenster", fetched.get(1).getTyp());
		assertEquals(1.0f, fetched.get(1).getValue(), 0.0001f);
		assertEquals(22.0f, fetched.get(0).getValue(), 0.0001f);
		db.disconnect();
		
		db.connect();
		fetched = db.getAllSensorData("test" + time);
		assertEquals(2, fetched.size());
		assertEquals(time, fetched.get(0).getUpdate());
		assertEquals("Temperatur", fetched.get(0).getTyp());
		assertEquals("Fenster", fetched.get(1).getTyp());
		assertEquals(1.0f, fetched.get(1).getValue(), 0.0001f);
		assertEquals(22.0f, fetched.get(0).getValue(), 0.0001f);
		db.disconnect();
	}

}