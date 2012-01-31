package vision.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;

import javax.sound.sampled.UnsupportedAudioFileException;


import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

/**
 * This class represents the plugins of the heater
 */
public class HeaterPlugin extends Plugin {

	/**
	 * @uml.property name="heaters"
	 */
	private List<Spatial> heaters = new ArrayList<Spatial>();
	private Spatial heater;
	private final Model model;
	private static final Logger LOG = Logger.getLogger(HeaterPlugin.class.getName());
	private View view;

	/**
	 * 
	 */
	public HeaterPlugin(Model model, View v) {
		super(model, new String[] { "heater" });
		this.model = model;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);		
	}
	
	/**
	 * align the heaters along walls
	 */
	private void alignHeaters() {
		for (Spatial g : heaters) {
			
		}
	}

	private void initHeaters(Application app) {
		heater = app.getAssetManager()
				.loadModel("Models/heater1.blend");
		
		for (Sensor s : getSensors()) {
			float temperature = 0;
			for (Sample sp : s.getMesswert()) {
				if (sp.getTyp().equals("Temperatur")) {
					temperature = sp.getValue();
					break;
				}
			}
			
			RigidBodyControl r = new RigidBodyControl(new BoxCollisionShape(new Vector3f(0.2f, 0.5f, 0.5f)), 0);
			r.setKinematic(false);
			
			heater.addControl(r);
			
			r.setPhysicsLocation(new Vector3f(s.getPosition().getX(), s.getPosition()
					.getY(), s.getPosition().getZ()));
			
			LOG.warning("Added foo at " + s.getPosition().getX() + s.getPosition()
					.getY() + s.getPosition().getZ());
			heater.setUserData("sensorid", s.getId());
			heaters.add(heater);
			((View)app).getRootNode().attachChild(heater);
			view = (View)app;
					
			app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(r);
		}
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		// TODO Auto-generated method stub
		super.stateDetached(stateManager);
		for (Spatial s : heaters) {
			view.getRootNode().detachChild(s);
		}
		view.getRootNode().detachChild(heater);
		heater = null;
	}
	
	

	private void updateHeaters() {
		LOG.info("Sensor state changed. Updating heater Objects.");
		final Material m = new Material(getApp().getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
		m.setBoolean("UseMaterialColors", true);
		
		
		m.setColor("Ambient",  ColorRGBA.Gray);
		m.setColor("Diffuse",  ColorRGBA.Gray);
		m.setColor("Specular", ColorRGBA.White);
		m.setFloat("Shininess", 3);
		
		for (Spatial g : heaters) {
			String sid = g.getUserData("sensorid");
			for (Sensor s : getSensors()) {
				if (!s.getId().equals(sid)) {
					continue;
				}
				for (Sample sp : s.getMesswert()) {
					if (sp.getTyp().equals("Temperatur")) {
						final float temperature = sp.getValue();
						LOG.info("Temperature for Heater with sensor id " + sid + " is " + temperature + sp.getUnit());
						ColorRGBA col = new ColorRGBA(temperature / 50f, 0, 1f - temperature / 50f, 1);
						final Material mat = m.clone();
						mat.setColor("Diffuse", col);
						mat.setColor("Ambient", col);
						g.depthFirstTraversal(new SceneGraphVisitor() {
							
							@Override
							public void visit(Spatial s) {
								if (s instanceof Geometry)
									((Geometry)s ).setMaterial(mat);
								
							}
						});
					}
				}
			}
		}
	}

	/**
	 * updates the client
	 */
	protected void clientUpdate(Application application, boolean changed) {
		if (heater == null)
			initHeaters(application);
		if (changed) {
			updateHeaters();
		}
	}
}
