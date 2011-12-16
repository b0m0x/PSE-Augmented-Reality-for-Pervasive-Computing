package vision.model;

import java.util.List;
import java.util.Collection;

import vision.view.Plugin;
import vision.view.View;

/**
 * provides a facade for all objects belonging to the model
 * 
 */
public class Model {

		
		
		public Model(View view){
		
				loadPlugins();
				getAllSensors();
				groundplan.load();
			 }

	private void loadPlugins() {
	}

	/**
					 */
	public void getSensordata(String id, int time) {
	}

	/**
						 */
	public void getTaggedSensors(List<String> tags) {
	}

	/** 
	 * @uml.property name="view"
	 * @uml.associationEnd inverse="daten:vision.view.View"
	 */
	private View view;

	/**
	 * @uml.property name="sensor"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="daten:vision.model.Sensor"
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
	 * @uml.associationEnd inverse="daten:vision.model.Database"
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
	 * @uml.associationEnd inverse="daten:vision.model.Update"
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
	 * @uml.associationEnd multiplicity="(1 1)" inverse="model:vision.model.PluginLoader"
	 */
	private PluginLoader pluginLoader = new vision.model.PluginLoader();

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
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="daten:vision.view.Plugin"
	 */
	private Collection<vision.view.Plugin> plugin;

	/**
	 * Getter of the property <tt>plugin</tt>
	 * 
	 * @return Returns the plugin.
	 * @uml.property name="plugin"
	 */
	public Collection<vision.view.Plugin> getPlugin() {
		return plugin;
	}

	/**
	 * Setter of the property <tt>plugin</tt>
	 * 
	 * @param plugin
	 *            The plugin to set.
	 * @uml.property name="plugin"
	 */
	public void setPlugin(Collection<vision.view.Plugin> plugin) {
		this.plugin = plugin;
	}

	/**
		 */
	private int getAllSensors() {
		return 0;
	}

	/** 
	 * @uml.property name="groundplan"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="model:vision.model.Groundplan"
	 */
	private Groundplan groundplan = new vision.model.Groundplan();

	/** 
	 * Getter of the property <tt>groundplan</tt>
	 * @return  Returns the groundplan.
	 * @uml.property  name="groundplan"
	 */
	public Groundplan getGroundplan() {
		return groundplan;
	}

	/** 
	 * Setter of the property <tt>groundplan</tt>
	 * @param groundplan  The groundplan to set.
	 * @uml.property  name="groundplan"
	 */
	public void setGroundplan(Groundplan groundplan) {
		this.groundplan = groundplan;
	}

	/**
	 * Getter of the property <tt>view</tt>
	 * @return  Returns the view.
	 * @uml.property  name="view"
	 */
	public View getView() {
		return view;
	}

	/**
	 * Setter of the property <tt>view</tt>
	 * @param view  The view to set.
	 * @uml.property  name="view"
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Getter of the property <tt>pluginLoader</tt>
	 * @return  Returns the pluginLoader.
	 * @uml.property  name="pluginLoader"
	 */
	public PluginLoader getPluginLoader() {
		return pluginLoader;
	}

	/**
	 * Setter of the property <tt>pluginLoader</tt>
	 * @param pluginLoader  The pluginLoader to set.
	 * @uml.property  name="pluginLoader"
	 */
	public void setPluginLoader(PluginLoader pluginLoader) {
		this.pluginLoader = pluginLoader;
	}

}
