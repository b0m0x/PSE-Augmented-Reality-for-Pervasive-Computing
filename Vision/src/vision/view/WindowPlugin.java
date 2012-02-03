package vision.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.vecmath.Quat4f;

import vision.controller.WindowController;
import vision.model.Hole;
import vision.model.HoleAdapter;
import vision.model.Model;
import vision.model.Position;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.Wall;
import vision.model.WallAdapter;

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

	public WindowPlugin(Model model, View view) {
		super(model, new String[] { "windowSpatial" });
		this.model = model;
		this.view = view;
	}

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

	private void initwindows(Application app) {
		windowclosed = app.getAssetManager().loadModel(
				"Models/window.j3o");
		windowopen = app.getAssetManager().loadModel(
				"Models/windowopen.blend");
		for (final Sensor sensor : getSensors()) {
			addwindowSpatial(sensor);
		}
		for (Wall w : model.getGroundplan().getWall()) {
			for (Hole h : w.getHole()) {
				WallAdapter wallAdapter = new WallAdapter(w);
				HoleAdapter holeAdapter = new HoleAdapter(h);
				Vector3f vec = createVector(wallAdapter, holeAdapter);
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

	protected void clientUpdate(Application application, boolean changed) {
		if (windowSpatial == null) {
			initwindows(application);
		}
		if (changed) {
			updatewindows();
		}
	}

	private void updatewindows() {
		for (Sensor s : getSensors()) {
			if (!windows.containsKey(s.getId())) {
				Spatial dummy = windowclosed;
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
				addwindowSpatial(s);
			}
		}
	}

	private void addwindowSpatial(final Sensor sensor) {
		windowSpatial = null;
		float status = 0;
		for (Sample sample : sensor.getMesswert()) {
			if (sample.getTyp().equals("windowSpatial")) {
				status = sample.getValue();
				break;
			}
		}
		if (status > 0) {
			windowSpatial = windowopen;
		} else {
			windowSpatial = windowclosed;
		}
		windowSpatial.setLocalTranslation(sensor.getPosition().getX(), sensor
				.getPosition().getY(), sensor.getPosition().getZ());
		Logger.getLogger(this.getClass().getName()).warning(
				"Added bar at " + windowSpatial.getLocalTranslation());
		windowSpatial = fitInHole(windowSpatial);
		Logger.getLogger(this.getClass().getName()).warning(
				"Move bar at " + windowSpatial.getLocalTranslation());
		windowSpatial.setUserData("sid", sensor.getId());
		windowSpatial.depthFirstTraversal(new SceneGraphVisitor() {

			@Override
			public void visit(Spatial arg0) {
				arg0.setName(sensor.getId() + "id");

			}
		});
		windows.put(sensor.getId(), windowSpatial);
		view.getRootNode().attachChild(windowSpatial.clone());
	}

	private Spatial fitInHole(Spatial windowSpatial) {
		List<Wall> walls = model.getGroundplan().getWall();
		Vector3f windowSpatialpos = windowSpatial.getLocalTranslation();
		Hole smallestHole = walls.get(0).getHole().get(0);
		Wall smallestWall = walls.get(0);
		float distance = 10000000.00f;
		for (Wall w : walls) {
			List<Hole> holes = w.getHole();
			for (Hole h : holes) {
				HoleAdapter holeAdapter = new HoleAdapter(h);
				WallAdapter wallAdapter = new WallAdapter(w);
				Vector3f HoleVec3 = new Vector3f(createVector(wallAdapter,
						holeAdapter));
				if (HoleVec3.distance(windowSpatialpos) < distance
						&& h.getPositionY1() > 0) {
					smallestHole = h;
					smallestWall = w;
					distance = HoleVec3.distance(windowSpatialpos);
				}
			}
		}
		HoleAdapter holeAdapter = new HoleAdapter(smallestHole);
		WallAdapter wallAdapter = new WallAdapter(smallestWall);
		float rotation = wallAdapter.getRotation();
		Vector3f HoleVec3f = new Vector3f(
				createVector(wallAdapter, holeAdapter));
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

	private Vector3f createVector(WallAdapter wallAdapter,
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

	private void createDummy(Vector3f vec, Application app) {
		windowSpatial = windowclosed.clone();
		windowSpatial.setLocalTranslation(vec);
		windowSpatial = fitInHole(windowSpatial);
		windowSpatial.setUserData("sid", "Dummy");
		
		windows.put(UUID.randomUUID().toString(), windowSpatial); // put it in so we can detach them later
		view.getRootNode().attachChild(windowSpatial);
	}
}
