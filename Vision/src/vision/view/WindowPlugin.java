package vision.view;

import java.util.List;

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
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.BlenderKey;
import com.jme3.bullet.util.Converter;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private List<Geometry> windows;
	private Geometry window;
	private Model model;
	private AnimChannel windowChannel;
	private AnimControl windowControl;

	public WindowPlugin(Model model, View view) {
		super(model, new String[] { "window" });
		this.model = model;
		setTags(new String[] { "window" });
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		BlenderKey blenderWindow = new BlenderKey("Models/window.blend");
		window = (Geometry) app.getAssetManager()
				.loadModel(blenderWindow);
		initWindows(app);
	}

	private void initWindows(Application app) {
		for (Sensor sensor : getSensors()) {
			float status = 0.0f;
			for (Sample sample : sensor.getMesswert()) {
				if (sample.getTyp().equals("window")) {
					status = sample.getValue();
					break;
				}
			}
			Material m = new Material(app.getAssetManager(),
					"Common/MatDefs/Misc/Unshaded.j3md");
			windowControl = window.getControl(AnimControl.class);
			windowChannel = windowControl.createChannel();
			if (status > 0.0f) {
				windowChannel.setAnim("opened");
			} else {
				windowChannel.setAnim("closed");
			}
			window.setMaterial(m);
			window.setLocalTranslation(sensor.getPosition().getX(), sensor
					.getPosition().getY(), sensor.getPosition().getZ());
			window = fitInHole(window);
			window.setUserData("sid", sensor.getId());
			windows.add(window.clone());
		}
	}

	protected void clientUpdate(Application application, boolean changed) {
		if (changed) {
			updateWindows();
		}
	}

	private void updateWindows() {
		for (Geometry g : windows) {
			String sid = g.getUserData("sid");
			for (Sensor sensor : getSensors()) {
				if (!sensor.getId().equals(sid)) {
					continue;
				}
				for (Sample sample : sensor.getMesswert()) {
					if (sample.getTyp().equals("window")) {
						float status = sample.getValue();
						windowControl = g.getControl(AnimControl.class);
						windowChannel = windowControl.createChannel();
						if (status > 0.0f) {
							windowChannel.setAnim("open");
						} else {
							windowChannel.setAnim("close");
						}
					}
				}
			}
		}
	}
	
	private Geometry fitInHole(Geometry window) {
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
				float newX = (float) (holevec2.getX() * Math.sin(rotation));
				float newY = (float) (holevec2.getX() * Math.cos(rotation));
				Vector3f HoleVec3 = new Vector3f(newX
						+ wallAdapter.getPosition().getX(), wallAdapter
						.getPosition().getY() + newY, holevec2.getY());
				if (HoleVec3.distance(windowpos) < distance) {
					smallestHole = h;
					smallestWall = w;
				}
			}
		}
		HoleAdapter holeAdapter = new HoleAdapter(smallestHole);
		Vector2f holevec2 = holeAdapter.getPosition();
		WallAdapter wallAdapter = new WallAdapter(smallestWall);
		float rotation = wallAdapter.getRotation();
		float newX = (float) (holevec2.getX() * Math.sin(rotation));
		float newY = (float) (holevec2.getY() * Math.cos(rotation));
		Vector3f HoleVec3f = new Vector3f(newX
				+ wallAdapter.getPosition().getX(), wallAdapter.getPosition()
				.getY() + newY, holevec2.getY());
		window.setLocalTranslation(HoleVec3f);
		Quat4f rot = new Quat4f();
		QuaternionUtil.setRotation(rot, new javax.vecmath.Vector3f(0, 1, 0),
				rotation);
		window.setLocalRotation(Converter.convert(rot));
		window.setLocalScale(holeAdapter.getSize().getX(), holeAdapter
				.getSize().getY(), wallAdapter.getDepth());
		return window;
	}
}
