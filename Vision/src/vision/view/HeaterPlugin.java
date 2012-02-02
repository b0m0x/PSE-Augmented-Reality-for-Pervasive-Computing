package vision.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;


import vision.model.Hole;
import vision.model.HoleAdapter;
import vision.model.MaterialHelper;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.Wall;
import vision.model.WallAdapter;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;

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
	private boolean debugHoles;

	/**
	 * 
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
		g.setLocalTranslation(findClosestHole(g.getLocalTranslation()));
		CollisionResults res = new CollisionResults();
		view.getRootNode().collideWith(g.getWorldBound(), res);
		if (res.size() == 0) { //No collisions - nothing to do
			return;
		} 
		//align to first colliding wall
		LOG.warning("rotate " + g.getName() + " because it collides with a wall");
		Geometry collidingWall = res.getCollision(0).getGeometry();
		g.setLocalRotation(new Quaternion(new float[] { 0, collidingWall.getWorldRotation().toAngleAxis(Vector3f.UNIT_Y), 0}));
		g.updateModelBound();
		

		view.getRootNode().collideWith(g.getWorldBound(), res);
		for (CollisionResult col : res) {
			//g.getLocalTranslation().addLocal(col.);
		}
	}

	private void initHeaters(Application app) {
		heaterSpatial = app.getAssetManager()
				.loadModel("Models/heater1.blend");
		
		for (Sensor s : getSensors()) {
			addHeaterSpatial(s);
		}
		updateHeaters();
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		// TODO Auto-generated method stub
		super.stateDetached(stateManager);
		for (Spatial s : heaters.values()) {
			view.getRootNode().detachChild(s);
		}
		view.getRootNode().detachChild(heaterSpatial);
		heaterSpatial = null;
		heaters.clear();
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
			for (Sample sp : s.getMesswert()) {
				if (sp.getTyp().equals("temperature")) {
					final float temperature = sp.getValue();

					final Material m = MaterialHelper.getInstance()
							.getColoredMaterial(getApp().getAssetManager(),
									temperature);
					LOG.info("Temperature for Heater with sensor id "
							+ s.getId() + " is " + temperature + sp.getUnit());

					Spatial h3d = heaters.get(s.getId());
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
	
	public Spatial getDebugMark() {
		 Arrow arrow = new Arrow(Vector3f.UNIT_Z.mult(2f));
		    arrow.setLineWidth(3);
		 
		    //Sphere sphere = new Sphere(30, 30, 0.2f);
		    Geometry mark = new Geometry("debug", arrow);
		    //mark = new Geometry("BOOM!", sphere);
		    Material mark_mat = new Material(view.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		    mark.setMaterial(mark_mat);
		    return mark;
	}
	
	private Vector3f findClosestHole(Vector3f pos) {
		Spatial debugMark = getDebugMark();
		Vector3f closestHole = new Vector3f();
		float distance = 10000.0f;
		for (Wall w : model.getGroundplan().getWall()) {
			WallAdapter wall = new WallAdapter(w);
			for (Hole h : wall.getHoles()) {
				if (h.getPositionY1() < 0.0001f) {
					continue; //ignore doors
				}
				HoleAdapter hole = new HoleAdapter(h);
				float xAbs = - (float) (Math.sin(- Math.PI / 2 + wall.getRotation()) * hole.getPosition().x) + wall.getEnd().getX();
				float zAbs = - (float) (Math.cos(- Math.PI / 2 + wall.getRotation()) * hole.getPosition().x) + wall.getEnd().getY();
				float yAbs = hole.getPosition().y - wall.getHeight() / 2f;
				
				Vector3f holeWorldPosition = new Vector3f(xAbs, yAbs, zAbs);
				if (!debugHoles) {
					debugMark.setLocalTranslation(holeWorldPosition.clone());
					view.getRootNode().attachChild(debugMark.clone());
				}
				float curDist = holeWorldPosition.distanceSquared(pos);
				if(curDist < distance) {
					distance = curDist;
					closestHole = holeWorldPosition;
				}
			}
		}
		debugHoles = true;
		return closestHole;

	}

	/**
	 * adds a heater object to the scene graph, using the data of the sensor
	 * @param s the sensor to use (position and samples will be used)
	 */
	private void addHeaterSpatial(final Sensor s) {
		LOG.warning("Position of heater " + s.getId() + ": " + s.getPosition().getX() + "; " + s.getPosition().getZ());
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
