package vision.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import vision.model.Model;
import vision.model.Sensor;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import vision.controller.PluginController;

public abstract class Plugin extends AbstractAppState {

	private static final Logger LOG = Logger.getLogger(Plugin.class.getName());
	
	/**
	 * @uml.property name="sensors"
	 */
	private List<Sensor> sensors;

	/**
	 * @uml.property name="tags"
	 */
	private String tags[];

	/**
	 * @uml.property name="daten"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="plugin:vision.model.Model"
	 */
	private Model daten;

	/**
	 * @uml.property name="app"
	 */
	private Application app;

	/**
	 * @uml.property name="pluginController"
	 * @uml.associationEnd inverse="plugin1:vision.controller.PluginController"
	 */
	private PluginController pluginController;

	private int lastSensorHashCode;

	/**
	 * Getter of the property <tt>sensors</tt>
	 * 
	 * @return Returns the sensors.
	 * @uml.property name="sensors"
	 */
	public List<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * Getter of the property <tt>tags</tt>
	 * 
	 * @return Returns the tags.
	 * @uml.property name="tags"
	 */
	public String[] getTags() {
		return tags;
	}

	public String getTag(int index) {
		return tags[index];
	}

	/**
	 * Setter of the property <tt>tags</tt>
	 * 
	 * @param tags
	 *            The tags to set.
	 * @uml.property name="tags"
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 */
	public Plugin(Model model, String[] tags) {
		setTags(tags);
		setDaten(model);
		updateSensors();
	}

	/**
	 */
	protected abstract void clientUpdate(Application application,
			boolean changed);

	private void updateSensors() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors = daten.getTaggedSensors(tags);
		setSensors(sensors);
		lastSensorHashCode = daten.getSensor().hashCode();
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		update(app);
	}

	public void update(Application application) {
		boolean changed = sensorsChanged();
		if (changed) {
			updateSensors();
		}
		clientUpdate(application, changed);
	}

	private boolean sensorsChanged() {
		return daten.getSensor().hashCode() != lastSensorHashCode;
	}

	/**
	 */
	protected Application getApplication() {
		return null;
	}

	/**
	 * Getter of the property <tt>daten</tt>
	 * 
	 * @return Returns the daten.
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
	 */
	public void initialize(AppStateManager stateManager, Application app) {
		this.app = app;
	}

	/**
	 * Getter of the property <tt>app</tt>
	 * 
	 * @return Returns the app.
	 * @uml.property name="app"
	 */
	public Application getApp() {
		return app;
	}

	/**
	 * Setter of the property <tt>sensors</tt>
	 * 
	 * @param sensors
	 *            The sensors to set.
	 * @uml.property name="sensors"
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	/**
	 * Getter of the property <tt>pluginController</tt>
	 * 
	 * @return Returns the pluginController.
	 * @uml.property name="pluginController"
	 */
	public PluginController getPluginController() {
		return pluginController;
	}

	/**
	 * Setter of the property <tt>pluginController</tt>
	 * 
	 * @param pluginController
	 *            The pluginController to set.
	 * @uml.property name="pluginController"
	 */
	public void setPluginController(PluginController pluginController) {
		this.pluginController = pluginController;
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		lastSensorHashCode = 0;
	}
}
