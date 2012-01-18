package vision.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import java.util.Collection;
import java.util.List;

import vision.model.CustomMesh;
import vision.model.Model;

/**
 * Renders all static objects and rooms
 */
public class MainAppState extends AbstractAppState implements ActionListener {
	private AppStateManager stateManager;
	private SimpleApplication app;
	private Model model;
	private Node mainGeometryNode = new Node("static");
	private boolean overviewCam;

	public MainAppState(Model model) {
		this.model = model;
		this.overviewCam = false;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.stateManager = stateManager;
		this.app = (SimpleApplication) app;
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		if (!isInitialized()) {
			return;
		}
		super.stateAttached(stateManager);
		mainGeometryNode = new Node("static");
		List<Geometry> staticObjects = model.getStaticGeometry();

		for (Geometry g : staticObjects) {
			mainGeometryNode.attachChild(g);
		}
		app.getRootNode().attachChild(mainGeometryNode);

		// load skybox
		Texture up = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryUP.tga");
		Texture dn = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryDN.tga");
		Texture lt = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryLT.tga");
		Texture rt = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryRT.tga");
		Texture ft = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryFT.tga");
		Texture bk = app.getAssetManager().loadTexture(
				"Texture/Skybox/CementaryBK.tga");
		Spatial sb = SkyFactory.createSky(app.getAssetManager(), ft, bk, lt,
				rt, up, dn);

		app.getRootNode().attachChild(sb);
		// init camera
		app.getFlyByCamera().setEnabled(true);

		// TODO: put this stuff in controller
		app.getInputManager().addMapping("select",
				new KeyTrigger(MouseInput.BUTTON_LEFT));
		app.getInputManager()
				.addMapping("zoom", new KeyTrigger(KeyInput.KEY_O));
		app.getInputManager().addListener(this,
				new String[] { "zoom", "select" });
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		app.getRootNode().detachChild(mainGeometryNode);
	}

	/**
	 * @uml.property name="wallMesh"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="mainAppState:vision.model.CustomMesh"
	 */
	private Collection<CustomMesh> wallMesh;

	/**
	 * Getter of the property <tt>wallMesh</tt>
	 * 
	 * @return Returns the wallMesh.
	 * @uml.property name="wallMesh"
	 */
	public Collection<CustomMesh> getWallMesh() {
		return wallMesh;
	}

	/**
	 * Setter of the property <tt>wallMesh</tt>
	 * 
	 * @param wallMesh
	 *            The wallMesh to set.
	 * @uml.property name="wallMesh"
	 */
	public void setWallMesh(Collection<CustomMesh> wallMesh) {
		this.wallMesh = wallMesh;
	}

	// TODO: refactor, put in controller
	@Override
	public void onAction(String name, boolean keyPressed, float tpf) {
		if (!overviewCam && name.equals("zoom")) {
			Vector3f oldCamloc = app.getCamera().getLocation();
			app.getCamera().setLocation(oldCamloc.add(new Vector3f(0, 0, 1f)));
			app.getCamera().lookAt(oldCamloc, new Vector3f(0, 0, 1));
			overviewCam = true;
		} else if (overviewCam && name.equals("zoom")) {
			Vector3f oldCamloc = app.getCamera().getLocation();
			app.getCamera().setLocation(oldCamloc.add(new Vector3f(0, 0, -1f)));
			// app.getCamera().lookAt(oldCamloc, new Vector3f(0, 0, 1));
			overviewCam = false;
		} else if (overviewCam && name.equals("select")) {
			CollisionResults results = new CollisionResults();
			Ray ray = new Ray(app.getCamera().getLocation(), app.getCamera()
					.getDirection());
			mainGeometryNode.collideWith(ray, results);
			for (int i = 0; i < results.size(); i++) {
				Vector3f pt = results.getCollision(i).getContactPoint();
				String hit = results.getCollision(i).getGeometry().getName();
				if (hit.equals("floor")) {
					app.getCamera().setLocation(
							pt.add(new Vector3f(0f, 0f, 1f)));
					overviewCam = false;
				}
			}
		}

	}
}
