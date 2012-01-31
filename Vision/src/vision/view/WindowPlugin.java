package vision.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.vecmath.Quat4f;

import vision.controller.WindowController;
import vision.model.Hole;
import vision.model.HoleAdapter;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.Wall;
import vision.model.WallAdapter;

import com.bulletphysics.linearmath.QuaternionUtil;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.util.Converter;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private List<Spatial> windows = new ArrayList<Spatial>();
	private Spatial window;
	private Spatial windowopen;
	private Spatial windowclosed;
	private Model model;
	Logger log = Logger.getLogger(WindowPlugin.class.getName());
	View view;

	public WindowPlugin(Model model, View view) {
		super(model, new String[] { "window" });
		this.model = model;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		if (isInitialized()) {
			return;
		}
		super.initialize(stateManager, app);
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		// TODO Auto-generated method stub
		super.stateDetached(stateManager);
		for (Spatial s : windows) {
			view.getRootNode().detachChild(s);
		}
		view.getRootNode().detachChild(window);
		window = null;
	}

	private void initWindows(Application app) {
		for (Sensor sensor : getSensors()) {
			float status = 0;
			for (Sample sample : sensor.getMesswert()) {
				if (sample.getTyp().equals("window")) {
					status = sample.getValue();
					break;
				}
			}
			windowclosed = app.getAssetManager().loadModel(
					"Models/window.blend");
			windowopen = app.getAssetManager().loadModel(
					"Models/windowopen.blend");
			if (status > 0) {
				window = app.getAssetManager().loadModel(
						"Models/windowopen.blend");
			} else {
				window = app.getAssetManager().loadModel("Models/window.blend");
			}
			window.setLocalTranslation(sensor.getPosition().getX(), sensor
					.getPosition().getY(), sensor.getPosition().getZ());
			Logger.getLogger(this.getClass().getName()).warning(
					"Added bar at " + sensor.getPosition().getX()
							+ sensor.getPosition().getY()
							+ sensor.getPosition().getZ());
			window = fitInHole(window);
			window.setUserData("sid", sensor.getId());
			windows.add(window);
			((View) app).getRootNode().attachChild(window);
			view = (View)app;
		}
	}

	protected void clientUpdate(Application application, boolean changed) {
		if (window == null) {
			initWindows(application);
		}
		if (changed) {
			updateWindows();
		}
	}

	private void updateWindows() {
		for (Spatial g : windows) {
			String sid = g.getUserData("sid");
			for (Sensor sensor : getSensors()) {
				if (!sensor.getId().equals(sid)) {
					continue;
				}
				for (Sample sample : sensor.getMesswert()) {
					if (sample.getTyp().equals("window")) {
						float status = sample.getValue();
						if (status > 0.0f) {
							window = windowopen;
						} else {
							window = windowclosed;
						}
					}
				}
			}
		}
	}

	private Spatial fitInHole(Spatial window) {
		List<Wall> walls = model.getGroundplan().getWall();
		Vector3f windowpos = window.getLocalTranslation();
		Hole smallestHole = walls.get(0).getHole().get(0);
		Wall smallestWall = walls.get(0);
		float distance = 10000000.00f;
		for (Wall w : walls) {
			List<Hole> holes = w.getHole();
			for (Hole h : holes) {
				HoleAdapter holeAdapter = new HoleAdapter(h);
				Vector2f holevec2 = holeAdapter.getPosition();
				WallAdapter wallAdapter = new WallAdapter(w);
				float rotation = wallAdapter.getRotation();
				float newX = (float) (wallAdapter.getStart().getX() + holevec2
						.getX() * Math.cos(rotation));
				float newY = (float) (holeAdapter.getPosition().getY() - wallAdapter
						.getHeight() / 2);
				float newZ = (float) (wallAdapter.getStart().getY() + holevec2
						.getX() * Math.sin(rotation));
				Vector3f HoleVec3 = new Vector3f(newX, newY, newZ);
				if (HoleVec3.distance(windowpos) < distance) {
					smallestHole = h;
					smallestWall = w;
					distance = HoleVec3.distance(windowpos);
				}
			}
		}
		HoleAdapter holeAdapter = new HoleAdapter(smallestHole);
		Vector2f holevec2 = holeAdapter.getPosition();
		WallAdapter wallAdapter = new WallAdapter(smallestWall);
		float rotation = wallAdapter.getRotation();
		float newX = (float) (wallAdapter.getEnd().getX() + holevec2.getX()
				* Math.cos(rotation));
		float newY = (float) (holeAdapter.getPosition().getY() - wallAdapter
				.getHeight() / 2);
		float newZ = (float) (wallAdapter.getEnd().getY() + holevec2.getX()
				* Math.sin(rotation - Math.PI));
		Vector3f HoleVec3f = new Vector3f(newX, newY, newZ);
		window.setLocalTranslation(HoleVec3f);
		Quat4f rot = new Quat4f();
		QuaternionUtil.setRotation(rot, new javax.vecmath.Vector3f(0, 1, 0),
				(float) (rotation + Math.PI / 2));
		window.setLocalRotation(Converter.convert(rot));
		if (window == windowopen) {
			window.setLocalScale(wallAdapter.getDepth(), holeAdapter.getSize()
					.getX(), holeAdapter.getSize().getY());
		} else {
			window.setLocalScale(wallAdapter.getDepth() *2, holeAdapter.getSize()
					.getX(), holeAdapter.getSize().getY());
		}
		return window;
	}
}
