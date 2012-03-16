package vision.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.vecmath.Quat4f;

import vision.controller.WindowController;
import vision.model.HoleAdapter;
import vision.model.Model;
import vision.model.Position;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.WallAdapter;
import vision.model.xml.Hole;
import vision.model.xml.Wall;

import com.bulletphysics.linearmath.QuaternionUtil;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.util.Converter;
import com.jme3.math.Vector3f;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private Map<String, Spatial> windows = new HashMap<String, Spatial>();
	private Spatial windowSpatial;
	private Spatial windowopen;
	private Spatial windowclosed;
	private Model model;
	private View view;
	Logger log = Logger.getLogger(WindowPlugin.class.getName());

	/**
	 * Constructs the WindowPlugin
	 * @param model
	 * @param view
	 */
	public WindowPlugin(Model model, View view) {
		super(model, new String[] { "window" });
		this.model = model;
		this.view = view;
	}

	/**
	 * initialization of the WindowPlugin
	 */
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		for (Spatial s : windows.values()) {
			view.getRootNode().detachChild(s);
		}
		view.getRootNode().detachChild(windowSpatial);
		windowSpatial = null;
		windows.clear();
	}

	/**
	 * loads window model from file
	 * puts a dummy window in every hole in the walls
	 * 
	 * @param app the application
	 */
	private void initwindows(Application app) {
		windowclosed = app.getAssetManager().loadModel(
				"Models/window.j3o");
		windowopen = app.getAssetManager().loadModel(
				"Models/windowopen.j3o");
		for (Wall w : model.getGroundplan().getWalls()) {
			for (Hole h : w.getHole()) {
				WallAdapter wallAdapter = new WallAdapter(w);
				HoleAdapter holeAdapter = new HoleAdapter(h);
				Vector3f vec = getWorldPosition(wallAdapter, holeAdapter);
				boolean empty = true;
				for (Spatial windowSpatial : windows.values()) {
					if (windowSpatial.getLocalTranslation().distance(vec) < 0.1f) {
						empty = false;
					}
				}
				if (empty) {
					createDummy(vec, app);
				}
			}
		}
		updatewindows();
	}

	/**
	 * checks if any windowPlugin as bin initialized
	 * if change has been detected calling update function of windowPlugin
	 */
	protected void clientUpdate(Application application, boolean changed) {
		if (windowSpatial == null) {
			initwindows(application);
		}
		if (changed) {
			updatewindows();
		}
	}

	/**
	 * checks if a sensor has been added, removes dummy from hole and puts sensor window in hole
	 */
	private void updatewindows() {
		for (Sensor s : getSensors()) {
			// checks if window already exists in list
			if (!windows.containsKey(s.getId())) {
				// if sensor is new remove dummy from hole
				Spatial dummy = windowclosed.clone();
				dummy.setLocalTranslation(s.getPosition().getX(), s
						.getPosition().getY(), s.getPosition().getZ());
				dummy = fitInHole(dummy);
				for (Spatial window : windows.values()) {
					if (window.getLocalTranslation().distance(
							dummy.getLocalTranslation()) < 0.1f && window.getUserData("sid").equals("Dummy")) {
						windows.remove(window);
						view.getRootNode().detachChild(window);
						
					}
				}
				// add new sensor in hole and list
				addwindowSpatial(s);
			} else {
				// updates window if new state of window is set
			for (Sample samp : s.getSamples()) {
				if (samp.getType().equals("window")) {
					view.getRootNode().detachChild(windows.get(s.getId()));
					windows.remove(s.getId());
					addwindowSpatial(s);
				}
				if (samp.getType().equals("light")) {
					view.getRootNode().detachChild(windows.get(s.getId()));
					windows.remove(s.getId());
					addwindowSpatial(s);
				}
			}
			}
		}
	}

	/**
	 * creates new window Spatial with given sensor parameters
	 * 
	 * @param sensor holds parameters for new Spatial
	 */
	private void addwindowSpatial(final Sensor sensor) {
		windowSpatial = null;
		float status = 0;
		float limit = 0;
		for (Sample sample : sensor.getSamples()) {

			if (sample.getType().equals("window")) {
				status = sample.getValue();
				limit = 0;
				break;
			}
			if (sample.getType().equals("light")) {
				status = sample.getValue();
				limit = 50;
				break;
			}
		}
		if (status > limit) {
			windowSpatial = windowopen.clone();
		} else {
			windowSpatial = windowclosed.clone();
		}
		windowSpatial.setLocalTranslation(sensor.getPosition().getX(), sensor
				.getPosition().getY(), sensor.getPosition().getZ());
		windowSpatial = fitInHole(windowSpatial);
		windowSpatial.setUserData("sid", sensor.getId());
		windowSpatial.depthFirstTraversal(new SceneGraphVisitor() {

			@Override
			public void visit(Spatial arg0) {
				arg0.setName(sensor.getId() + "id");

			}
		});
		windows.put(sensor.getId(), windowSpatial);
		view.getRootNode().attachChild(windowSpatial);
	}

	/**
	 * searches neares hole and puts window into it
	 * 
	 * @param windowSpatial object to fit in hole
	 * @return new position and shape of windowSpatial
	 */
	private Spatial fitInHole(Spatial windowSpatial) {
		List<Wall> walls = model.getGroundplan().getWalls();
		Vector3f windowSpatialpos = windowSpatial.getLocalTranslation();
		Hole smallestHole = walls.get(0).getHole().get(0);
		Wall smallestWall = walls.get(0);
		float distance = 10000000.00f;
		// searches closest hole to windowSpatial
		for (Wall w : walls) {
			List<Hole> holes = w.getHole();
			for (Hole h : holes) {
				HoleAdapter holeAdapter = new HoleAdapter(h);
				WallAdapter wallAdapter = new WallAdapter(w);
				Vector3f HoleVec3 = new Vector3f(getWorldPosition(wallAdapter,
						holeAdapter));
				if (HoleVec3.distance(windowSpatialpos) < distance
						&& h.getPositionY1() > 0) {
					smallestHole = h;
					smallestWall = w;
					distance = HoleVec3.distance(windowSpatialpos);
				}
			}
		}
		// sets new Location of windowSpatial into hole
		HoleAdapter holeAdapter = new HoleAdapter(smallestHole);
		WallAdapter wallAdapter = new WallAdapter(smallestWall);
		float rotation = wallAdapter.getRotation();
		Vector3f HoleVec3f = new Vector3f(
				getWorldPosition(wallAdapter, holeAdapter));
		windowSpatial.setLocalTranslation(HoleVec3f);
		Quat4f rot = new Quat4f();
		QuaternionUtil.setRotation(rot, new javax.vecmath.Vector3f(0, 1, 0),
				(float) (rotation + Math.PI / 2));
		windowSpatial.setLocalRotation(Converter.convert(rot));
		if (windowSpatial.equals(windowopen)) {
			windowSpatial.setLocalScale(wallAdapter.getDepth(), holeAdapter
					.getSize().getX(), holeAdapter.getSize().getY());
		} else {
			windowSpatial.setLocalScale(wallAdapter.getDepth() * 2, holeAdapter
					.getSize().getX(), holeAdapter.getSize().getY());
		}
		return windowSpatial;
	}

	/**
	 * calculates worldPosition from given parameters
	 * 
	 * @param wallAdapter wall with Position data
	 * @param holeAdapter hole with local Position data
	 * @return world position data of hole
	 */
	private Vector3f getWorldPosition(WallAdapter wallAdapter,
			HoleAdapter holeAdapter) {
		float newX = -(float) (Math.sin(-Math.PI / 2
				+ wallAdapter.getRotation()) * holeAdapter.getPosition().x)
				+ wallAdapter.getEnd().getX();
		float newY = (float) (holeAdapter.getPosition().getY() - wallAdapter
				.getHeight() / 2);
		float newZ = -(float) (Math.cos(-Math.PI / 2
				+ wallAdapter.getRotation()) * holeAdapter.getPosition().x)
				+ wallAdapter.getEnd().getY();
		Vector3f HoleVec3 = new Vector3f(newX, newY, newZ);
		return HoleVec3;
	}

	/**
	 * creates dummy window for empty holes
	 * 
	 * @param vec position of dummy window
	 * @param app the application
	 */
	private void createDummy(Vector3f vec, Application app) {
		windowSpatial = windowclosed.clone();
		windowSpatial.setLocalTranslation(vec);
		windowSpatial = fitInHole(windowSpatial);
		windowSpatial.setUserData("sid", "Dummy");
		
		windows.put(UUID.randomUUID().toString(), windowSpatial); // put it in so we can detach them later
		view.getRootNode().attachChild(windowSpatial);
	}
}
