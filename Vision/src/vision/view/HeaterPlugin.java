package vision.view;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;



import vision.model.HoleAdapter;
import vision.model.MaterialHelper;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.WallAdapter;
import vision.model.xml.Hole;
import vision.model.xml.Wall;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

/**
 * This class represents the plugins of the heater
 */
public class HeaterPlugin extends Plugin {

	/**
	 * @uml.property name="heaters"
	 */
	private Spatial heaterSpatial;
	private final Model model;
	private static final Logger LOG = Logger.getLogger(HeaterPlugin.class.getName());
	private View view;
	private Map<String, Spatial> heaters = new HashMap<String, Spatial>();
	private Map<String, Light> heaterLights = new HashMap<String, Light>();

	/**
	 * Constructs the Heaterplugin.
	 */
	public HeaterPlugin(Model model, View v) {
		super(model, new String[] { "heater" });
		this.model = model;
		view = v;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);		
	}
	
	/**
	 * align the heaters along walls
	 */
	private void alignHeater(Spatial g) {
		moveToClosestHole(g);
	}

	private void initHeaters(Application app) {
		heaterSpatial = app.getAssetManager()
				.loadModel("Models/heater1.j3o");
		
		for (Sensor s : getSensors()) {
			addHeaterSpatial(s);
		}
		updateHeaters();
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		for (Spatial s : heaters.values()) {
			view.getRootNode().detachChild(s);
		}
		view.getRootNode().detachChild(heaterSpatial);
		heaterSpatial = null;
		heaters.clear();
		
		for (Light l : heaterLights.values()) {
			view.getRootNode().removeLight(l);
		}
		heaterLights.clear();
	}
	
	

	private void updateHeaters() {
		LOG.info("Sensor state changed. Updating heater Objects.");

		if (getSensors().size() > heaters.size()) {
			for (Sensor s : getSensors()) {
				if (!heaters.containsKey(s.getId())) {
					addHeaterSpatial(s);
				}
			}
		}

		for (Sensor s : getSensors()) {
			for (Sample sp : s.getSamples()) {
				if (sp.getType().equals("temperature")) {
					final float temperature = sp.getValue();

					final Material m = MaterialHelper.getInstance()
							.getColoredMaterial(getApp().getAssetManager(),
									temperature);
					LOG.info("Temperature for Heater with sensor id "
							+ s.getId() + " is " + temperature + sp.getUnit());

					Spatial h3d = heaters.get(s.getId());
					
					ColorRGBA col = new ColorRGBA();
					col.set(temperature / 50f, 0, 1 - temperature / 50f, 1);
					Light l = heaterLights.get(s.getId());
					if (l != null) {
						l.setColor(col);
					}
					if (h3d == null) {
						continue;
					}
					
					h3d.depthFirstTraversal(
							new SceneGraphVisitor() {

								@Override
								public void visit(Spatial s) {
									if (s instanceof Geometry) {
										((Geometry) s).setMaterial(m);
										LOG.info("Setting a color");
									}
								}
							});
				}
			}

		}
	}
	
	/**
	 * moves the heater under the closest window hole
	 * @param g the heater to move
	 */
	private void moveToClosestHole(Spatial g) {
		Vector3f pos = g.getLocalTranslation();
		Vector3f closestHole = new Vector3f();
		WallAdapter closestWall = null;
		float distance = 10000.0f;
		for (Wall w : model.getGroundplan().getWalls()) {
			WallAdapter wall = new WallAdapter(w);
			for (Hole h : wall.getHoles()) {
				if (h.getPositionY1() < 0.0001f) {
					continue; //ignore doors
				}
				HoleAdapter hole = new HoleAdapter(h);
				float xAbs = - (float) (Math.sin(- Math.PI / 2 + wall.getRotation()) * hole.getPosition().x) + wall.getEnd().getX();
				float zAbs = - (float) (Math.cos(- Math.PI / 2 + wall.getRotation()) * hole.getPosition().x) + wall.getEnd().getY();
				float yAbs = h.getPositionY1() - wall.getHeight() / 2f - 0.30f;
				
				Vector3f holeWorldPosition = new Vector3f(xAbs, yAbs, zAbs);
				float curDist = holeWorldPosition.distanceSquared(pos);
				if(curDist < distance) {
					distance = curDist;
					closestHole = holeWorldPosition;
					closestWall = wall;
				}
			}
		}
		g.setLocalRotation(new Quaternion(new float[] {0, closestWall.getRotation(), 0}));
		Vector3f diff = closestHole.subtract(g.getLocalTranslation());
		Vector3f wallNormal = new Vector3f(closestWall.getEnd().getX() - closestWall.getStart().getX(), 0 , closestWall.getEnd().getY() - closestWall.getStart().getY()).cross(Vector3f.UNIT_Y);
		if (wallNormal.dot(diff) > 0) {
			wallNormal.negateLocal();
		}
		wallNormal.normalizeLocal();
		
		g.setLocalTranslation(closestHole.add(wallNormal.mult(0.3f)));

	}

	/**
	 * adds a heater object and a light to the scene graph, using the data of the sensor
	 * @param s the sensor to use (position and samples will be used)
	 */
	private void addHeaterSpatial(final Sensor s) {
		heaterSpatial.setLocalTranslation(new Vector3f(s.getPosition().getX(), s.getPosition()
				.getY() - 1.0f, s.getPosition().getZ()));
		heaterSpatial.setUserData("sensorid", s.getId());
		Spatial h = heaterSpatial.clone();
		h.breadthFirstTraversal(new SceneGraphVisitor() {
			
			@Override
			public void visit(Spatial arg0) {
				arg0.setName(s.getId() + "id");				
			}
		});
		heaters.put(s.getId(), h);
		alignHeater(h);
		view.getRootNode().attachChild(h);
		
		PointLight l = createHeaterLight(); 
		heaterLights.put(s.getId(), l);
		l.setPosition(h.getLocalTranslation());
		view.getRootNode().addLight(l);
	}
	
	PointLight createHeaterLight() {
		PointLight pl = new PointLight();
		pl.setName("heaterlight");
		pl.setRadius(4.f);
		return pl;
	}

	/**
	 * updates the client
	 */
	protected void clientUpdate(Application application, boolean changed) {
		if (heaterSpatial == null)
			initHeaters(application);
		if (changed) {
			updateHeaters();
		}
	}
}
