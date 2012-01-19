package vision.view;

import java.util.List;
import java.util.logging.Logger;


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
 * This class represents the plugins of the heater
 */
public class HeaterPlugin extends Plugin {

	/**
	 * @uml.property name="heaters"
	 */
	private List<Geometry> heaters;
	private Geometry heater;
	private Model model;
	private Logger log = Logger.getLogger(HeaterPlugin.class.getName());

	/**
	 * 
	 */
	public HeaterPlugin(Model model, View v) {
		super(model);
		this.model = model;
		setTags(new String[] { "heater" });
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		heater = (Geometry) app.getAssetManager()
				.loadModel("Models/heater.j3o");
		initHeaters(app);
	}
	
	/**
	 * align the heaters along walls
	 */
	private void alignHeaters() {
		for (Geometry g : heaters) {
			//TODO do it
		}
	}

	private void initHeaters(Application app) {
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

	private void updateHeaters() {
		for (Geometry g : heaters) {
			String sid = g.getUserData("sid");
			for (Sensor s : getSensors()) {
				if (!s.getId().equals(sid)) {
					continue;
				}
				for (Sample sp : s.getMesswert()) {
					if (sp.getTyp().equals("Temperatur")) {
						float temperature = sp.getValue();
						g.getMaterial().setColor(
								"Color",
								new ColorRGBA(temperature / 50f, 0,
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
