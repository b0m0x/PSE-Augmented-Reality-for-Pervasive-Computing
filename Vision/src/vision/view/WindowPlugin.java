package vision.view;

import java.util.ArrayList;
import java.util.List;

import vision.controller.WindowController;
import vision.model.Hole;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;
import vision.model.Wall;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private List<Geometry> windows;
	private Geometry windowopened;
	private Geometry windowclosed;
	private Geometry window;
	private Model model;

	/**
	 */
	public WindowPlugin(Model model, View view) {
		super(model);
		this.model = model;
		setTags(new String[] { "window" });
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		windowopened = (Geometry) app.getAssetManager().loadModel(
				"Models/windowopened.j3o");
		windowclosed = (Geometry) app.getAssetManager().loadModel(
				"Models/windowclosed.j3o");
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
			if (status > 0.0f) {
				window.setMesh(windowopened.getMesh());
			} else {
				window.setMesh(windowclosed.getMesh());
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
						if(status > 0.0f) {
							g.setMesh(windowopened.getMesh());
						} else {
							g.setMesh(windowclosed.getMesh());
						}
					}
				}
			}
		}
	}
	
	private Geometry fitInHole(Geometry window) {
		List<Wall> walls = model.getGroundplan().getWall();
		Vector3f windowpos = window.getLocalTranslation();
		for(Wall w: walls) {
			List<Hole> holes = w.getHole();
			for(Hole h: holes) {
				float top = h.getHeightWindow() + h.getHeightGround();
				float bottom = h.getHeightGround();
				float startX = h.getPositionX1();
				float startY = h.getPositionY1();
				float endX = h.getPositionX2();
				float endY = h.getPositionY2();
				Vector3f vecStart = new Vector3f(startX, startY, bottom);
				Vector3f vecEnd = new Vector3f(endX, endY, top);
				float distanceStart = vecStart.distance(windowpos);
				float distanceEnd = vecEnd.distance(windowpos);
			}
		}
		return window;
	}

}
