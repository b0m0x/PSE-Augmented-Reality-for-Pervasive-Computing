package vision.view;

import java.util.List;

import vision.controller.WindowController;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private List<Geometry> windows;
	private Geometry window;

	/**
	 */
	public WindowPlugin(Model model, View view) {
		super(model);
		setTags(new String[] { "window" });
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		window = (Geometry) app.getAssetManager()
				.loadModel("Models/window.j3o");
		initWindows(app);
	}

	private void initWindows(Application app) {
		for (Sensor sensor : getSensors()) {
			boolean open = false;
			for (Sample sample : sensor.getMesswert()) {
				if (sample.getTyp().equals("offen")) {
					open = true;
					break;
				}
			}
			Material m = new Material(app.getAssetManager(),
					"Common/MatDefs/Misc/Unshaded.j3md");
			if (open) {
				window.open();
			} else {
				window.close();
			}
			window.setLocalScale(sensor.getPosition().getX(), sensor
					.getPosition().getY(), sensor.getPosition().getZ());
			window.setUserData("sid", sensor.getId());
			windows.add(window.clone());
		}
	}

	protected void clientUpdate(Application application, boolean changed) {
		if(changed) {
			updateWindows();
		}

	}
	
	private void updateWindows() {
		for (Geometry g : windows) {
			String sid = g.getUserData("sid");
			for(Sensor sensor: getSensors()) {
				if(!sensor.getId().equals(sid)) {
					continue;
				}
				for(Sample sample: sensor.getMesswert()) {
					if(sample.getTyp().equals("zu")) {
						window.close();
					} else {
						window.open();
					}
				}
			}
		}
	}
	

}
