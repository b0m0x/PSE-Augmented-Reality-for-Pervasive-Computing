package vision.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.h2.jdbc.JdbcSQLException;

/**
 * This class manages the database connection and saves sensordata.
 */
public class Database {

	private static final String CREATE_STATEMENT = "CREATE TABLE Samples ("
			+ "id VARCHAR(30), " + "Zeitpunkt LONG, " + "Type VARCHAR(30), "
			+ "Unit VARCHAR(30), " + "Updated LONG, " + "Value FLOAT, "
			+ "Tags OBJECT)";
	
	private static final String DROP_STATEMENT = "DROP TABLE Samples IF EXISTS";
	
	private static final String FETCH_ALL_QUERY = "SELECT * FROM Samples";

	private static final String COUNT_QUERY = "SELECT COUNT(*) AS rowcount FROM Samples";

	private static final Logger LOG = Logger
			.getLogger(Database.class.getName());


	private Connection conn;
	private boolean inUse = false;

	/**
	 * delete all samples
	 */
	public void dropTable() {
		Statement st;
		try {
			st = conn.createStatement();
			st.execute(DROP_STATEMENT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean tableExists(String tableName) {
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet rs = dbm.getTables(null, null, tableName, null);
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * saves a sensor object in the database
	 */
	public final void updateSensors(String id, long zeitpunkt,
			Sample messwerte, List<String> tags) {

		try {
			String insert = "INSERT INTO SAMPLES VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst;
			Statement st = null;
			if (!inUse) {
				st = conn.createStatement();
			} else {
				return;
			}

			if (!tableExists("SAMPLES")) {
				st.execute(CREATE_STATEMENT);
			}
			//Set parameters for the database statement
			pst = conn.prepareStatement(insert);
			pst.setString(1, id);
			pst.setLong(2, zeitpunkt);
			pst.setString(3, messwerte.getType());
			pst.setString(4, messwerte.getUnit());
			pst.setLong(5, messwerte.getUpdate());
			pst.setFloat(6, messwerte.getValue());
			pst.setObject(7, tags);
			
			//execute statement
			pst.executeUpdate();
			
			LOG.info("Stored: " + id + " ");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * fetches the sensor samples collected.
	 * 
	 * @return samples
	 */
	public List<Sample> getSensordata(String id, long zeitpunkt) {
		List<Sample> samples = new ArrayList<Sample>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(FETCH_ALL_QUERY);
			while (rs.next()) {
				if (rs.getString("id").equals(id)
						&& rs.getInt("zeitpunkt") == zeitpunkt) {
					Sample sample = new Sample();
					sample.setType(rs.getString("Type"));
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
	 * @param id id of the sensor	 * 
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
				sample.setType(rs.getString("Type"));
				sample.setUnit(rs.getString("Unit"));
				sample.setUpdate(rs.getLong("Updated"));
				sample.setValue(rs.getFloat("Value"));
				samples.add(sample);
			}
			return samples;
		} catch (SQLException e) {
			LOG.warning("Error getting all Sensor Data");
			// System.exit(0);
			return null;
		}

	}

	/**
	 * returns all samples of a sensor inbetween a given interval.
	 * 
	 * @param id
	 *            id of the sensor
	 * @param from
	 *            timestamp of beginning
	 * @param to
	 *            timestamp of the end of the interval
	 * @return a list of all sensor samples belonging to the given sensor
	 */
	public List<Sample> getSensorDataInterval(final String id, final long from,
			final long to) {
		List<Sample> samples = new ArrayList<Sample>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(FETCH_ALL_QUERY);
			while (rs.next()) {
				if (rs.getString("id").equals(id)
						&& rs.getInt("zeitpunkt") >= from
						&& rs.getInt("zeitpunkt") <= to) {
					Sample sample = new Sample();
					sample.setType(rs.getString("Type"));
					sample.setUnit(rs.getString("Unit"));
					sample.setUpdate(rs.getLong("Updated"));
					sample.setValue(rs.getFloat("Value"));
					samples.add(sample);
				}
			}
			return samples;
		} catch (SQLException e) {
			LOG.severe("Error getting data from Interval");
			return Collections.emptyList();
		}
	}

	/**
	 * returns the number of stored samples
	 * @return the number of stored samples, 0 in case of error
	 */
	public int size() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(COUNT_QUERY);
			rs.next();
			return rs.getInt("rowcount");
		} catch (SQLException e) {
			return 0;
		}
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
			e.printStackTrace();
		}
	}

	/**
	 * gets the connection.
	 * 
	 * @return
	 */
	public Connection getConn() {
		return this.conn;
	}

}