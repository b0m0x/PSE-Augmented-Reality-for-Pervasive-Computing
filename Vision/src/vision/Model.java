package vision;

import java.util.List;
import java.util.Collection;

/**
 * This class manages all the data of the system. Moreover it passes the Data to
 * the Controller and the View.
 */
public class Model {

	/**
	 * loads the plugins
	 */
	public void loadPlugins() {
	}

	/**
	 * gets the Sensordata of a specific sensor at a specific time.
	 * 
	 * @param id
	 *            id of the Sensor
	 * @param time
	 *            time of the data taken
	 * @return List of Samples of the Sensor at the given time or NULL
	 */
	public List<Sample> getSensorSamples(String id, int time) {
		throw new UnsupportedOperationException();
	}

	/**
	 * gets all Sensors with the passed tags
	 * 
	 * @param tags tags to search for
	 * @return returns List of Sensors with given tags
	 */
	public List<Sensor> getTaggedSensors(List<String> tags) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @uml.property name="view"
	 * @uml.associationEnd inverse="daten:vision.View"
	 * 
	 */
	private View view;

	/**
	 * Getter of the property <tt>view</tt>
	 * 
	 * @return Returns the view.
	 * @uml.property name="view"
	 */
	public View getView() {
		return view;
	}

	/**
	 * Setter of the property <tt>view</tt>
	 * 
	 * @param view
	 *            The view to set.
	 * @uml.property name="view"
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @uml.property name="sensor"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="daten:vision.Sensor"
	 */
	private Collection<Sensor> sensor;

	/**
	 * Getter of the property <tt>sensor</tt>
	 * 
	 * @return Returns the sensor.
	 * @uml.property name="sensor"
	 */
	public Collection<Sensor> getSensor() {
		return sensor;
	}

	/**
	 * @uml.property name="datenbank"
	 * @uml.associationEnd inverse="daten:vision.Database"
	 */
	private Database datenbank;

	/**
	 * Getter of the property <tt>datenbank</tt>
	 * 
	 * @return Returns the datenbank.
	 * @uml.property name="datenbank"
	 */
	public Database getDatenbank() {
		return datenbank;
	}

	/**
	 * Setter of the property <tt>datenbank</tt>
	 * 
	 * @param datenbank
	 *            The datenbank to set.
	 * @uml.property name="datenbank"
	 */
	public void setDatenbank(Database datenbank) {
		this.datenbank = datenbank;
	}

	/**
	 * @uml.property name="update"
	 * @uml.associationEnd inverse="daten:vision.Update"
	 */
	private Update update;

	/**
	 * Getter of the property <tt>update</tt>
	 * 
	 * @return Returns the update.
	 * @uml.property name="update"
	 */
	public Update getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * 
	 * @param update
	 *            The update to set.
	 * @uml.property name="update"
	 */
	public void setUpdate(Update update) {
		this.update = update;
	}

	/**
	 * Setter of the property <tt>sensor</tt>
	 * 
	 * @param sensor
	 *            The sensor to set.
	 * @uml.property name="sensor"
	 */
	public void setSensor(Collection<Sensor> sensor) {
		this.sensor = sensor;
	}

	/**
	 * @uml.property name="pluginLoader"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="model:vision.PluginLoader"
	 */
	private PluginLoader pluginLoader = new vision.PluginLoader();

	/**
	 * Getter of the property <tt>pluginLoader</tt>
	 * 
	 * @return Returns the pluginLoader.
	 * @uml.property name="pluginLoader"
	 */
	public PluginLoader getPluginLoader() {
		return pluginLoader;
	}

	/**
	 * Setter of the property <tt>pluginLoader</tt>
	 * 
	 * @param pluginLoader
	 *            The pluginLoader to set.
	 * @uml.property name="pluginLoader"
	 */
	public void setPluginLoader(PluginLoader pluginLoader) {
		this.pluginLoader = pluginLoader;
	}

	/**
	 * @uml.property name="pluginList"
	 */
	private List<Plugin> pluginList;

	/**
	 * Getter of the property <tt>pluginList</tt>
	 * 
	 * @return Returns the pluginList.
	 * @uml.property name="pluginList"
	 */
	public List<Plugin> getPluginList() {
		return pluginList;
	}

	/**
	 * Setter of the property <tt>pluginList</tt>
	 * 
	 * @param pluginList
	 *            The pluginList to set.
	 * @uml.property name="pluginList"
	 */
	public void setPluginList(List<Plugin> pluginList) {
		this.pluginList = pluginList;
	}

	/**
	 * @uml.property name="plugin"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="daten:vision.Plugin"
	 */
	private Collection<vision.Plugin> plugin;

	/**
	 * Getter of the property <tt>plugin</tt>
	 * 
	 * @return Returns the plugin.
	 * @uml.property name="plugin"
	 */
	public Collection<vision.Plugin> getPlugin() {
		return plugin;
	}

	/**
	 * Setter of the property <tt>plugin</tt>
	 * 
	 * @param plugin
	 *            The plugin to set.
	 * @uml.property name="plugin"
	 */
	public void setPlugin(Collection<vision.Plugin> plugin) {
		this.plugin = plugin;
	}

}
