package vision.model;

import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 * manages the database connection and saves sensordata
 * 
 * 
 */
public class Database {

	private Connection conn;

	private boolean tableExists(String tableName) {
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet rs = dbm.getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * saves a sensor object in the database
	 */
	public void updateSensors(String id, int zeitpunkt, Sample messwerte) {
		Statement st;
		if (tableExists("Samples")) {
			try {
				st = conn.createStatement();
				st.executeUpdate("INSERT INTO Samples " + "VALUES ( " + id
						+ ", " + zeitpunkt + ", " + messwerte + ")");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			try {
				st = conn.createStatement();
				st.execute("create table Samples (" + "id INTEGER, "
						+ "Zeitpunkt INTEGER " + "Sample Sample)");
				st.executeUpdate("INSERT INTO Samples " + "VALUES ( " + id
						+ ", " + zeitpunkt + ", " + messwerte + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * fetches the sensor samples collected
	 */
	public List<Sample> getSensordata(int id, int zeitpunkt) {
		List<Sample> sampleList = Collections.emptyList();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getInt("id") == id
						&& rs.getInt("zeitpunkt") == zeitpunkt) {
					Sample sample = (Sample) rs.getObject("Sample");
					sampleList.add(sample);
				}
			}

			return sampleList;

		} catch (SQLException e) {
			System.out.println("Error getting Sensor Data!");
			System.exit(0);
			return null;
		}
	}

	/**
	 * returns all stored samples of a sensor
	 * 
	 * @param id
	 *            id of the sensor
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getAllSensorData(int id) {
		List<Sample> samples = Collections.emptyList();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getInt("id") == id) {
					Sample sample = (Sample) rs.getObject("Sample");
					samples.add(sample);
				}
			}
			return samples;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting all Sensor Data");
			System.exit(0);
			return null;
		}

	}

	/**
	 * returns all samples of a sensor inbetween a given interval
	 * 
	 * @param id
	 *            id of the sensor
	 * @param from
	 *            timestamp of beginning
	 * @param to
	 *            timestamp of the end of the interval
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getSensorDataInterval(int id, long from, long to) {
		List<Sample> samples = Collections.emptyList();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getInt("id") == id && rs.getInt("zeitpunkt") >= from
						&& rs.getInt("zeitpunkt") <= to) {
					Sample sample = (Sample) rs.getObject("Sample");
					samples.add(sample);
				}
			}
			return samples;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getting data from Interval");
			System.exit(0);
			return null;
		}
	}

	public void connect() {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:db", "user", "pw");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return this.conn;
	}

}
