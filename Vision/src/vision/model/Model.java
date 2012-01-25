package vision.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import de.lessvoid.nifty.builder.HoverEffectBuilder;

import java.util.Date;

import vision.Config;
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
		sensor = createTestSensors();
		this.view = view;
		loadPlugins();

		this.datenbank = new vision.model.Database();
		/*
		 * updater = new UpdateThread(this); updater.start();
		 */

		Logger.getLogger("").setLevel(Config.logLevel);

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
	 * ei
	 */
	// TODO fix
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
	private List<Sensor> sensor;

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

	private void createGeometry() {
		staticGeometries = new ArrayList<Spatial>();
		Material m = new Material(view.getAssetManager(),
				"Common/MatDefs/Light/Lighting.j3md");
		m.setBoolean("UseMaterialColors", true);
		m.setColor("Ambient", ColorRGBA.Gray);
		m.setColor("Diffuse", ColorRGBA.Gray);
		m.setColor("Specular", ColorRGBA.White);

		Texture tex = view.getAssetManager().loadTexture(
				"Texture/walltexture.jpg");
		tex.setWrap(WrapMode.Repeat);
		m.setTexture("DiffuseMap", tex);
		m.setFloat("Shininess", 3);

		for (Wall w : groundplan.getWall()) {
			Spatial g = new CustomMeshCreator().convert(w);
			g.setMaterial(m);
			staticGeometries.add(g);
		}

		// add hardcoded floor
		Geometry floor = new Geometry("floor", new Box(20f, 0.1f, 50f));
		floor.setLocalTranslation(0, -1.7f, 0);
		floor.setMaterial(m);
		floor.addControl(new RigidBodyControl(0));

		Geometry ceiling = new Geometry("ceiling", new Box(20f, 0.1f, 50f));
		ceiling.setLocalTranslation(0, 1.7f, 0);
		ceiling.setMaterial(m);
		ceiling.addControl(new RigidBodyControl(0));

		staticGeometries.add(floor);
		staticGeometries.add(ceiling);
	}

	protected List<Sensor> createTestSensors() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		Sensor s = new Sensor();
		s.setId("testSensor");
		s.addToTags("heater");
		s.addToSamples(new Sample("Temperatur", "°C", 25.0f, System
				.currentTimeMillis()));
		s.setPosition(new Position(2, -0.5f, 1));
		sensors.add(s);

		List<Wall> walls = groundplan.getWall();
		int i = 0;
		for (Wall w : walls) {
			List<Hole> holes = w.getHole();
			WallAdapter wAdapter = new WallAdapter(w);
			for (Hole h : holes) {
				if (h.getPositionY1() > 0) {
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
		}
		return sensors;
	}

	protected void close() {
		updater.setRunning(false);
	}
}
