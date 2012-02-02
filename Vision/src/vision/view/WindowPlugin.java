package vision.view;

import java.util.ArrayList;
import java.util.List;
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
		windowclosed = app.getAssetManager().loadModel(
				"Models/window.j3o");
		windowopen = app.getAssetManager().loadModel(
				"Models/windowopen.blend");
		for (final Sensor sensor : getSensors()) {
			window = null;
			float status = 0;
			for (Sample sample : sensor.getMesswert()) {
				if (sample.getTyp().equals("window")) {
					status = sample.getValue();
					break;
				}
			}
			if (status > 0) {
				window = windowopen;
			} else {
				window = windowclosed;
			}
			window.setLocalTranslation(sensor.getPosition().getX(), sensor
					.getPosition().getY(), sensor.getPosition().getZ());
			Logger.getLogger(this.getClass().getName()).warning(
					"Added bar at " + window.getLocalTranslation());
			window = fitInHole(window);
			Logger.getLogger(this.getClass().getName()).warning(
					"Move bar at " + window.getLocalTranslation());
			window.setUserData("sid", sensor.getId());
			window.depthFirstTraversal(new SceneGraphVisitor() {
				
				@Override
				public void visit(Spatial arg0) {
					arg0.setName(sensor.getId() + "id");
					
				}
			});
			windows.add(window);
			((View) app).getRootNode().attachChild(window.clone());
			view = (View)app;
		}
		for(Wall w : model.getGroundplan().getWall()) {
			for(Hole h : w.getHole()) {
				WallAdapter wallAdapter = new WallAdapter(w);
				HoleAdapter holeAdapter = new HoleAdapter(h);
				Vector3f vec = createVector(wallAdapter, holeAdapter);
				boolean empty = true;
				for(Spatial window : windows) {
					if(window.getLocalTranslation().distance(vec) < 0.1f) {
						empty = false;
					}
				}
				if(empty) {
					createDummy(vec, app);
				}
			}
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
				WallAdapter wallAdapter = new WallAdapter(w);
				Vector3f HoleVec3 = new Vector3f(createVector(wallAdapter, holeAdapter));
				if (HoleVec3.distance(windowpos) < distance && h.getPositionY1() > 0) {
					smallestHole = h;
					smallestWall = w;
					distance = HoleVec3.distance(windowpos);
				}
			}
		}
		HoleAdapter holeAdapter = new HoleAdapter(smallestHole);
		WallAdapter wallAdapter = new WallAdapter(smallestWall);
		float rotation = wallAdapter.getRotation();
		Vector3f HoleVec3f = new Vector3f(createVector(wallAdapter, holeAdapter));
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
	
	private Vector3f createVector(WallAdapter wallAdapter, HoleAdapter holeAdapter) {
		float newX = - (float) (Math.sin(- Math.PI / 2 + wallAdapter.getRotation()) * holeAdapter.getPosition().x) + wallAdapter.getEnd().getX();
		float newY = (float) (holeAdapter.getPosition().getY() - wallAdapter
				.getHeight() / 2);
		float newZ = - (float) (Math.cos(- Math.PI / 2 + wallAdapter.getRotation()) * holeAdapter.getPosition().x) + wallAdapter.getEnd().getY();
		Vector3f HoleVec3 = new Vector3f(newX, newY, newZ);
		return HoleVec3;
	}
	
	private void createDummy(Vector3f vec, Application app) {
		Sensor sensor = new Sensor();
		sensor.addToTags("window");
		sensor.addToSamples(new Sample("window", "bool", 0.0f,
				System.currentTimeMillis()));
		sensor.setId("Dummy");
		sensor.setPosition(new Position(vec.getX(), vec.getY(), vec.getZ()));
		window = windowclosed;
		window.setLocalTranslation(vec);
		window = fitInHole(window);
		window.setUserData("sid", sensor.getId());
		windows.add(window);
		((View) app).getRootNode().attachChild(window.clone());
		view = (View)app;
	}
}
