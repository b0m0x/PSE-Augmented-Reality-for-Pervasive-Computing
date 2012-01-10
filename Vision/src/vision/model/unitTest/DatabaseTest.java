package vision.model.unitTest;

import vision.model.Database;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
	
	Database db = new Database();
	
	public void testUpdate() {
		
	}
	
	public void testGetSensorData() {
		assertNotNull(db.getSensordata(0, 0));
	}
	
	public void testGetAllSensorData() {
		assertNotNull(db.getAllSensorData(0));
	}
	
	public void testGetSensorDataInterval() {
		assertNotNull(db.getSensorDataInterval(0, 0, 0));
	}

}
