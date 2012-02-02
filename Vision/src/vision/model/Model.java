package vision.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import vision.Config;
import vision.controller.PluginController;
import vision.view.Plugin;
import vision.view.View;

/**
 * provides a facade for all objects belonging to the model
 * 
 */
public class Model {

	UpdateThread updater;

	public Model(View view) throws JAXBException {

		this.groundplan = new vision.model.Groundplan().load();
//		 sensor = createTestSensors();
		sensor = Collections.emptyList();
		this.view = view;
		loadPlugins();

		updater = new UpdateThread(this);
		updater.start();

		Logger.getLogger("").setLevel(Config.LOG_LEVEL);

	}

	private void loadPlugins() {
		pluginList = pluginLoader.loadPlugins(this, view);
		pluginControllerList = pluginLoader.getController();
	}

	/**
					 */
	public List<Sample> getSensordata(String id, int time) {
		return getDatenbank().getSensordata(id, time);
	}

	/**
	 * ei
	 */
	public synchronized List<Sensor> getTaggedSensors(String[] tags) {
		if (sensor == null) {
			return Collections.emptyList();
		}
		List<Sensor> tagged = new ArrayList<Sensor>();
		for (Sensor s : sensor) {
			for (String tag : tags) {
				if (s.getTags().contains(tag)) {
					tagged.add(s);
				}
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
	private List<Sensor> sensor;

	/**
	 * Getter of the property <tt>sensor</tt>
	 * 
	 * @return Returns the sensor.
	 * @uml.property name="sensor"
	 */
	public synchronized List<Sensor> getSensor() {
		return sensor;
	}


	/**
	 * Getter of the property <tt>datenbank</tt>
	 * 
	 * @return Returns the datenbank.
	 * @uml.property name="datenbank"
	 */
	public Database getDatenbank() {
		return updater.getUpdate().getDatabase();
	}


	/**
	 * Setter of the property <tt>sensor</tt>
	 * 
	 * @param sensor
	 *            The sensor to set.
	 * @uml.property name="sensor"
	 */
	public synchronized void setSensor(List<Sensor> sensor) {
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
	private List<Plugin> pluginList = Collections.emptyList();

	private List<PluginController> pluginControllerList = Collections
			.emptyList();

	/**
	 * Getter of the property <tt>pluginList</tt>
	 * 
	 * @return Returns the pluginList.
	 * @uml.property name="pluginList"
	 */
	public List<Plugin> getPluginList() {
		return pluginList;
	}

	public List<PluginController> getPluginControllerList() {
		return pluginControllerList;
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
		return getSensor();
	}

	/**
	 * @uml.property name="groundplan"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="model:vision.model.Groundplan"
	 */
	private Groundplan groundplan;

	private List<Spatial> staticGeometries;

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

	public List<Spatial> getStaticGeometry() {
		if (staticGeometries == null) {
			createGeometry();
		}
		return staticGeometries;
	}

	public List<Light> getLights() {
		return groundplan.getLight();
	}

	private void createGeometry() {
		staticGeometries = new ArrayList<Spatial>();
		Material m = MaterialHelper.getInstance().getWallMaterial(
				view.getAssetManager());
		Material fm = MaterialHelper.getInstance().getFloorMaterial(
				view.getAssetManager());

		CustomMeshCreator meshCreator = new CustomMeshCreator();

		for (Wall w : groundplan.getWall()) {
			Spatial g = meshCreator.convert(w);
			g.setMaterial(m);
			staticGeometries.add(g);
		}

		for (StaticGeometry sg : groundplan.getStaticGeometry()) {
			Spatial geo = view.getAssetManager().loadModel(sg.getPath());
			geo.rotate(0, sg.getAngle(), 0);
			geo.setLocalTranslation(sg.getX(), 0, sg.getY());
			staticGeometries.add(geo);
		}
		
		Node ceilingNode = new Node("ceiling");

		for (FloorCeiling fc : groundplan.getFloorCeiling()) {
			float MPX = (fc.getBenchmarks().get(0).getPositionX() + fc
					.getBenchmarks().get(1).getPositionX()) / 2;
			float MPY = (fc.getBenchmarks().get(0).getPositionY() + fc
					.getBenchmarks().get(1).getPositionY()) / 2;
			float SX = Math.abs((fc.getBenchmarks().get(0).getPositionX() - fc
					.getBenchmarks().get(1).getPositionX()) / 2);
			float SY = Math.abs((fc.getBenchmarks().get(0).getPositionY() - fc
					.getBenchmarks().get(1).getPositionY()) / 2);
			Geometry ceiling = (Geometry) meshCreator.createCeiling(
					new Vector3f(MPX, fc.getCeilingHeight(), MPY),
					new Vector3f(SX, 0.1f, SY));
			ceiling.setMaterial(m);
			Geometry floor = (Geometry) meshCreator.createFloor(new Vector3f(
					MPX, -(fc.getCeilingHeight()), MPY), new Vector3f(SX, 0.1f,
					SY));
			floor.setMaterial(fm);
			
			ceilingNode.attachChild(ceiling);
			staticGeometries.add(floor);
		}
		staticGeometries.add(ceilingNode);
	}

	public List<Reference> getReferencePoints() {
		return groundplan.getReference();
	}

	protected List<Sensor> createTestSensors() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		Sensor s = new Sensor();
		s.setId("testSensor");
		s.addToTags("heater");
		s.addToSamples(new Sample("temperature", "°C", 15.0f, System
				.currentTimeMillis()));
		s.setPosition(new Position(0, 0, 0));
		sensors.add(s);

		List<Wall> walls = groundplan.getWall();
		int i = 0;
		for (Wall w : walls) {
			List<Hole> holes = w.getHole();
			WallAdapter wAdapter = new WallAdapter(w);
			for (Hole h : holes) {
				
					Sensor sensor = new Sensor();
					sensor.addToTags("window");
					sensor.addToSamples(new Sample("window", "bool", 0.0f,
							System.currentTimeMillis()));
					HoleAdapter holeAdapter = new HoleAdapter(h);
					Vector2f holevec2 = holeAdapter.getPosition();
					sensor.setId("SensorNr:" + i);
					float rotation = wAdapter.getRotation();
					float newX = (float) (holevec2.getX() * Math.cos(rotation) + wAdapter
							.getStart().getX());
					float newY = (float) (holeAdapter.getPosition().getY() - wAdapter
							.getHeight() / 2);
					float newZ = (float) (holevec2.getX() * Math.sin(rotation) + wAdapter
							.getStart().getY());
					Vector3f HoleVec3f = new Vector3f(newX, newY, newZ);
					sensor.setPosition(new Position(HoleVec3f.getX(), HoleVec3f
							.getY(), HoleVec3f.getZ()));
					sensors.add(sensor);
				
			}
		}
		return sensors;
	}

	public void close() {
		if (updater != null) {
			updater.setRunning(false);
		}
	}
}
