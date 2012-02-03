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

import org.h2.jdbc.JdbcSQLException;

/**
 * This class manages the database connection and saves sensordata.
 */
public class Database {

	private static final Logger LOG = Logger
			.getLogger(Database.class.getName());
	private Connection conn;
	private boolean inUse = false;

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
	public final void updateSensors(String id, long zeitpunkt, Sample messwerte,
			List<String> tags) {

		try {
			String insert = "INSERT INTO SAMPLES VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst;
			Statement st = null;
			if (!inUse) {
				st = conn.createStatement();
			} else {
				return;
			}

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
	 * fetches the sensor samples collected.
	 * @return samples
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
	 * returns all stored samples of a sensor.
	 * 	 * @param id
	 *            id of the sensor
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public final List<Sample> getAllSensorData(String id) {
		List<Sample> samples = new ArrayList<Sample>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Samples WHERE id = '"
					+ id + "'");
			while (rs.next()) {
				Sample sample = new Sample();
				sample.setTyp(rs.getString("Type"));
				sample.setUnit(rs.getString("Unit"));
				sample.setUpdate(rs.getLong("Updated"));
				sample.setValue(rs.getFloat("Value"));
				samples.add(sample);
			}
			return samples;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.warning("Error getting all Sensor Data");
			// System.exit(0);
			return null;
		}

	}

	/**
	 * returns all samples of a sensor inbetween a given interval.
	 * @param id
	 *            id of the sensor
	 * @param from
	 *            timestamp of beginning
	 * @param to
	 *            timestamp of the end of the interval
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getSensorDataInterval(final String id, final long from, final long to) {
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
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * Gets the IDs.
	 * @param index
	 * @return
	 */
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
	/**
	 * Gets the tags and creates a list.
	 * @param index
	 * @return
	 */
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
	
	/**
	 * 
	 * @param bg
	 */
	public synchronized void connect(UpdateThread bg) {
		try {
			// Class.forName("org.h2.Driver");
			try {
				wait(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn = DriverManager.getConnection("jdbc:h2:database/db",
						"user", "pw");
			} catch (JdbcSQLException e) {
				inUse = true;
				LOG.warning("Database already in use. Please close all instances of \"Vision\"...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void disconnect() {
		try {
			if (!inUse) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * gets the connection.
	 * @return
	 */
	public Connection getConn() {
		return this.conn;
	}

}