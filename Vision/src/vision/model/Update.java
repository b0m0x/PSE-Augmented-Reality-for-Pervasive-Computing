package vision.model;

import java.util.List;
import java.sql.*;

/**
 * manages the server connection and fetches the sensor data
 * 
 */
public class Update {

	private Connection conn;

	/**
	 * @uml.property name="daten"
	 * @uml.associationEnd inverse="update:vision.model.Model"
	 */
	/**
	 * provides a facade for all objects belonging to the model
	 * 
	 */

	private Model daten;

	/**
	 * Getter of the property <tt>daten</tt>
	 * 
	 * @return Returns the daten.arg0
	 * @uml.property name="daten"
	 */
	public Model getDaten() {
		return daten;
	}

	/**
	 * Setter of the property <tt>daten</tt>
	 * 
	 * @param daten
	 *            The daten to set.
	 * @uml.property name="daten"
	 */
	public void setDaten(Model daten) {
		this.daten = daten;
	}

	/**
	 * @uml.property name="jSONConverter"
	 * @uml.associationEnd inverse="update:vision.model.JSONConverter"
	 */
	private JSONConverter jsonConverter;

	/**
	 * Getter of the property <tt>jSONConverter</tt>
	 * 
	 * @return Returns the jsonConverter.
	 * @uml.property name="jSONConverter"
	 */
	public JSONConverter getJSONConverter() {
		return jsonConverter;
	}

	/**
	 * Setter of the property <tt>jSONConverter</tt> arg0
	 * 
	 * @param jSONConverter
	 *            The jsonConverter to set.
	 * @uml.property name="jSONConverter"
	 */
	public void setJSONConverter(JSONConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	/**
	 * @uml.property name="database"
	 * @uml.associationEnd inverse="update:vision.model.Database"
	 */
	private Database database;

	/**
	 * Getter of the property <tt>database</tt>
	 * 
	 * @return Returns the database.
	 * @uml.property name="database"
	 */
	public Database getDatabase() {
		return database;
	}

	private void connect() {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:", "", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Setter of the property <tt>database</tt>
	 * 
	 * @param database
	 *            The database to set.
	 * @uml.property name="database"
	 */
	public void setDatabase(Database database) {
		this.database = database;
	}

	public void store(int time) {
		List<Sensor> sensorList = jsonConverter.getSensorList();
		this.connect();
		try {
			Statement statement = conn.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
		 */
	public void getAllData() {
	}

}
