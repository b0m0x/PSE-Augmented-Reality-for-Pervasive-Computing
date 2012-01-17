package vision.view;

import java.util.List;

import vision.controller.HeaterController;
import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * @author idle This class represents the plugins of the heater
 */
public class HeaterPlugin extends Plugin {

	/**
	 * @uml.property name="heaters"
	 */
	private List<Geometry> heaters;
	private Geometry heater;
	/**
	 * 
	 */
	public HeaterPlugin(Model model, View v) {
		super(model);
		setTags(new String[] { "heater" });
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		heater = (Geometry) app.getAssetManager()
				.loadModel("Models/heater.j3o");
		initHeaters(app);
	}
	
	void initHeaters(Application app) {
		for (Sensor s : getSensors()) {
			float temperature = 0;
			for (Sample sp : s.getMesswert()) {
				if (sp.getTyp().equals("Temperatur")) {
					temperature = sp.getValue();
					break;
				}
			}
			Material m = new Material(app.getAssetManager(),
					"Common/MatDefs/Misc/Unshaded.j3md");
			m.setColor("Color", new ColorRGBA(temperature / 50f, 0,
					1 - temperature / 50f, 1));
			heater.setMaterial(m);
			heater.setLocalTranslation(s.getPosition().getX(), s.getPosition()
					.getY(), s.getPosition().getZ());
			heater.setUserData("sid", s.getId());
			heaters.add(heater.clone());
		}
	}
	
	void updateHeaters() {
		for (Geometry g : heaters) {
			String sid = g.getUserData("sid");
			for (Sensor s : getSensors()) {
				if (!s.getId().equals(sid)) {
					continue;
				}
				for (Sample sp : s.getMesswert()) {
					if (sp.getTyp().equals("Temperatur")) {
						float temperature = sp.getValue();
						g.getMaterial().setColor("Color", new ColorRGBA(temperature / 50f, 0,
								1 - temperature / 50f, 1));
					}
				}
			}
		}
	}

	/**
	 * updates the client
	 */
	protected void clientUpdate(Application application, boolean changed) {
		if (changed) {
			updateHeaters();
		}
		return;
	}

}
