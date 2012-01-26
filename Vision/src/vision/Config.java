package vision;

import java.util.logging.Level;

/**
 * Holds global configuration variables
 */
public class Config {
	/**
	 * defines where to fetch the sensor updates
	 */
	public static final String serverUrl = "http://cumulus.teco.edu:51525/sensor/entity";

	/**
	 * defines how often the sensor data is refreshed
	 */
	public static final int updateIntervall = 13000;

	/**
	 * 
	 */
	public static final String pluginpath = "plugins/";
	
	public static final String groundplanPath = "assets/groundplan.xml";
	
	public static final Level logLevel = Level.WARNING;
}
