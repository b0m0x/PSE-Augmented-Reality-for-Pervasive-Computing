package vision.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import vision.view.Plugin;
import vision.view.View;

/**
 * provides a facade for all objects belonging to the model
 * 
 */
public class Model {

	public Model(View view) throws JAXBException {
		this.view = view;
		// loadPlugins();
		this.sensor = getAllSensors();
		this.groundplan = new vision.model.Groundplan().load();
		this.datenbank = new vision.model.Database();

	}

	private void loadPlugins() {
		pluginList = pluginLoader.loadPlugins(this, view);
	}

	/**
					 */
	public List<Sample> getSensordata(String id, int time) {
		return datenbank.getSensordata(id, time);
	}

	/**
						 */
	public List<Sensor> getTaggedSensors(List<String> tags) {
		List<Sensor> tagged = new ArrayList<Sensor>();
		for (int i = 0; i < sensor.size(); i++) {
			if (sensor.get(i).getTags().equals(tags)) {
				tagged.add(sensor.get(i));
			}
		}
		return tagged;
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
	private List<Sensor> sensor = new ArrayList<Sensor>();

	/**
	 * Getter of the property <tt>sensor</tt>
	 * 
	 * @return Returns the sensor.
	 * @uml.property name="sensor"
	 */
	public List<Sensor> getSensor() {
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
	public void setSensor(List<Sensor> sensor) {
		this.sensor = sensor;
	}

	/**
	 * @uml.property name="pluginLoader"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="model:vision.model.PluginLoader"
	 */
	private PluginLoader pluginLoader = new vision.model.PluginLoader();

	/**
	 * @uml.property name="pluginList"
	 */
	private List<Plugin> pluginList = Collections.EMPTY_LIST;

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
		 */
	private List<Sensor> getAllSensors() {
		return new vision.model.JSONConverter().getSensorList();
	}

	/**
	 * @uml.property name="groundplan"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="model:vision.model.Groundplan"
	 */
	private Groundplan groundplan;

	/**
	 * Getter of the property <tt>groundplan</tt>
	 * 
	 * @return Returns the groundplan.
	 * @uml.property name="groundplan"
	 */
	public Groundplan getGroundplan() {
		return groundplan;
	}

	/**
	 * Setter of the property <tt>groundplan</tt>
	 * 
	 * @param groundplan
	 *            The groundplan to set.
	 * @uml.property name="groundplan"
	 */
	public void setGroundplan(Groundplan groundplan) {
		this.groundplan = groundplan;
	}

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

	public List<Geometry> getStaticGeometry() {
		List<Geometry> l = new ArrayList<Geometry>();
		Material m = new Material(view.getAssetManager(),
				"Common/MatDefs/Misc/Unshaded.j3md");
		m.setTexture("ColorMap",
				view.getAssetManager().loadTexture("Interface/Logo/Monkey.jpg"));
		m.getAdditionalRenderState().setWireframe(true);
		m.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
		for (int i = 0; i < groundplan.getWall().size(); i++) {
			Geometry g = new Geometry("floor");
			g.setMesh(new Box(Math.abs(groundplan.getWall().get(i)
					.getPositionX1()
					- groundplan.getWall().get(i).getPositionX2()), Math
					.abs(groundplan.getWall().get(i).getPositionY1()
							- groundplan.getWall().get(i).getPositionY2()),
					groundplan.getWall().get(i).getWide()));
			// g = new CustomMeshCreator().convert(groundplan.getWall().get(i));
			g.setMaterial(m);
			l.add(g);
		}
		return l;
	}
}
