package vision.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * manages the database connection and saves sensordata
 * 
 * 
 */
public class Database {

	private static final Logger LOG = Logger.getLogger(Database.class.getName());
	private Connection conn;

	private boolean tableExists(String tableName, Connection conn) {
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet rs = dbm.getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			} else {
				return false;
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
	public void updateSensors(String id, long zeitpunkt, Sample messwerte,
			List<String> tags) {

		try {
			String insert = "INSERT INTO SAMPLES VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst;
			Statement st = conn.createStatement();

			if (tableExists("SAMPLES", conn)) {
				pst = conn.prepareStatement(insert);
				pst.setString(1, id);
				pst.setLong(2, zeitpunkt);
				pst.setString(3, messwerte.getTyp());
				pst.setString(4, messwerte.getUnit());
				pst.setLong(5, messwerte.getUpdate());
				pst.setFloat(6, messwerte.getValue());
				pst.setObject(7, tags);
				pst.executeUpdate();
				LOG.info("Stored: " + id + " ");
			} else {
				st.execute("create table Samples (" + "id VARCHAR(30), "
						+ "Zeitpunkt LONG, " + "Type VARCHAR(30), "
						+ "Unit VARCHAR(30), " + "Updated LONG, "
						+ "Value FLOAT, " + "Tags OBJECT)");
				pst = conn.prepareStatement(insert);
				pst.setString(1, id);
				pst.setLong(2, zeitpunkt);
				pst.setString(3, messwerte.getTyp());
				pst.setString(4, messwerte.getUnit());
				pst.setLong(5, messwerte.getUpdate());
				pst.setFloat(6, messwerte.getValue());
				pst.setObject(7, tags);
				pst.executeUpdate();
				LOG.info("Database created and stored: " + id + " ");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * fetches the sensor samples collected
	 */
	public List<Sample> getSensordata(String id, long zeitpunkt) {
		List<Sample> samples = new ArrayList<Sample>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getString("id").equals(id)
						&& rs.getInt("zeitpunkt") == zeitpunkt) {
					Sample sample = new Sample();
					sample.setTyp(rs.getString("Type"));
					sample.setUnit(rs.getString("Unit"));
					sample.setUpdate(rs.getLong("Updated"));
					sample.setValue(rs.getFloat("Value"));
					samples.add(sample);
				}
			}

			return samples;

		} catch (SQLException e) {
			LOG.warning("Error getting Sensor Data!");
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
	public List<Sample> getAllSensorData(String id) {
		List<Sample> samples = new ArrayList<Sample>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getString("id").equals(id)) {
					Sample sample = new Sample();
					sample.setTyp(rs.getString("Type"));
					sample.setUnit(rs.getString("Unit"));
					sample.setUpdate(rs.getLong("Updated"));
					sample.setValue(rs.getFloat("Value"));
					samples.add(sample);
				}
			}
			return samples;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.warning("Error getting all Sensor Data");
			//System.exit(0);
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
	public List<Sample> getSensorDataInterval(String id, long from, long to) {
		List<Sample> samples = new ArrayList();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				if (rs.getString("id").equals(id)
						&& rs.getInt("zeitpunkt") >= from
						&& rs.getInt("zeitpunkt") <= to) {
					Sample sample = new Sample();
					sample.setTyp(rs.getString("Type"));
					sample.setUnit(rs.getString("Unit"));
					sample.setUpdate(rs.getLong("Updated"));
					sample.setValue(rs.getFloat("Value"));
					samples.add(sample);
				}
			}
			return samples;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.warning("Error getting data from Interval");
			return null;
		}
	}

	public int size() {

		int i = 0;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Samples");
			while (rs.next()) {
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public String getIDs(int index) {
		String s = "";
		Statement st;
		int i = 0;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from Samples");
			while (rs.next()) {
				if (index == i) {
					s = rs.getString("id");
				}
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return s;
	}

	public List<String> getTags(int index) {
		List<String> tags = new ArrayList<String>();
		Statement st;
		int i = 0;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from Samples");
			while (rs.next()) {
				if (i == index) {
					tags = (List<String>) rs.getObject("Tags");
				}
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tags;
	}

	public void connect() {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:database/db", "user",
					"pw");
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