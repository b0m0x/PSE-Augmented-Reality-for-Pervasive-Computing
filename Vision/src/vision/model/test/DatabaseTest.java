package vision.model.test;

import java.util.ArrayList;
import java.util.List;

import vision.model.Database;
import vision.model.JSONConverter;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.Update;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {

	Database db = new Database();

	public void testUpdate() {
		Update update = new Update();
		update.getDatabase().connect();
		update.getJSONConverter().convert();
		update.store(30);
		update.getDatabase().disconnect();
	}

	public void testGetSensorData() {
		Update update = new Update();
		update.getDatabase().connect();
		List<Sample> samples = new ArrayList<Sample>();
		for (int i = 0; i < update.getJSONConverter().getSensorList().size(); i++) {
			Sensor sensor = update.getJSONConverter().getSensorList().get(i);
			System.out.println("ID: " + sensor.getId());
			System.out.println("Database ID: " + i + "\n");
			samples = update.getDatabase().getSensordata(sensor.getId(), 30);
			for (int j = 0; j < samples.size(); j++) {
				Sample sample = samples.get(j);
				System.out.println("Sample: " + j);
				System.out.println("Type: " + sample.getTyp());
				System.out.println("Unit: " + sample.getUnit());
				System.out.println("Last updated: " + sample.getUpdate());
				System.out.println("Value: " + sample.getValue() + "\n");
			}
			System.out
					.println("----------------------------------------------------------------------------");
		}

		update.getDatabase().disconnect();
	}

	// public void testSize() {
	// Database db = new Database();
	// db.connect();
	// System.out.println(db.size());
	// System.out.println();
	// db.disconnect();
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

	public void testGetAllSensorData() {
		Update update = new Update();
		update.getDatabase().connect();
		update.getAllData();
		List<Sample> samples = new ArrayList<Sample>();
		for (int i = 0; i < update.getDatabase().size(); i++) {
			System.out.println("ID: " + update.getDatabase().getIDs(i));
			System.out.println("Database ID: " + i);
			samples = update.getDatabase().getAllSensorData(
					update.getDatabase().getIDs(i));
			for (int j = 0; j < samples.size(); j++) {
				Sample sample = samples.get(j);
				System.out.println("Sample: " + j);
				System.out.println("Type: " + sample.getTyp());
				System.out.println("Unit: " + sample.getUnit());
				System.out.println("Last updated: " + sample.getUpdate());
				System.out.println("Value: " + sample.getValue() + "\n");
			}
		}

		update.getDatabase().disconnect();

	}

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

}