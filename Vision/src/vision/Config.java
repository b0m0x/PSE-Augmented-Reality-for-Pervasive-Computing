package vision;

import java.util.logging.Level;

/**
 * Holds global configuration variables
 */
public class Config {
	/**
	 * defines where to fetch the sensor updates
	 */
	public static final String SERVER_URL = "http://cumulus.teco.edu:51525/sensor/entity";

	/**
	 * defines how often the sensor data is refreshed
	 */
	public static final int UPDATE_INTERVAL = 13000;

	/**
	 * 
	 */
	public static final String PLUGIN_PATH = "plugins/";
	
	public static final String GROUNDPLAN_PATH = "assets/groundplan.xml";
	
	public static final Level LOG_LEVEL = Level.WARNING;
}
