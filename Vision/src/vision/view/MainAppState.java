package vision.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import vision.controller.Controller;
import vision.model.CustomMesh;
import vision.model.Model;

/**
 * Renders all static objects and rooms
 */
public class MainAppState extends AbstractAppState {
	private AppStateManager stateManager;
	private Controller controller;
	private SimpleApplication app;
	private Model model;
	private Node mainGeometryNode = new Node("static");
	private boolean overviewCam;
	private Logger log = Logger.getLogger(this.getClass().getName());
	private CharacterControl player;
	
	public MainAppState(Model model, Controller contoller) {
		this.model = model;
		this.overviewCam = false;
		this.controller = contoller;
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
		BulletAppState bas = stateManager.getState(BulletAppState.class);
		PhysicsSpace pSpace = new PhysicsSpace();
		if (bas == null) {
			log.warning("No BulletAppState has been attached - continuing without physics");
		} else {
			pSpace = bas.getPhysicsSpace();
		}
		
		mainGeometryNode = new Node("static");
		List<Spatial> staticObjects = model.getStaticGeometry();

		for (Spatial g : staticObjects) {
			mainGeometryNode.attachChild(g);
			
			//add physics control
			pSpace.add(g.getControl(0));
		}
		CapsuleCollisionShape pcs = new CapsuleCollisionShape(1.5f, 6f, 1);
		player = new CharacterControl(pcs, 0.02f);
		player.setFallSpeed(5f);
		player.setGravity(30f);
		player.setPhysicsLocation(new Vector3f(0,50,0));
		pSpace.add(player);
		
		
		app.getRootNode().attachChild(mainGeometryNode);

		// load skybox
		Spatial sb = loadSkyBox();

		app.getRootNode().attachChild(sb);
		
		
		//add light
		PointLight lamp_light = new PointLight();
		lamp_light.setColor(ColorRGBA.Yellow);
		lamp_light.setRadius(40f);
		lamp_light.setPosition(new Vector3f(0, 0, 5));
		app.getRootNode().addLight(lamp_light);


		app.getRootNode().setCullHint(CullHint.Never);
		
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		app.getRootNode().addLight(al);
		
		// init camera
		app.getFlyByCamera().setEnabled(true);

		// TODO: put this stuff in controller
		app.getInputManager().addMapping("select",
				new KeyTrigger(MouseInput.BUTTON_LEFT));
		app.getInputManager()
				.addMapping("zoom", new KeyTrigger(KeyInput.KEY_O));
		app.getInputManager().addListener(controller,
				new String[] { "zoom", "select" });
	}

	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		app.getCamera().setLocation(player.getPhysicsLocation());
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		app.getRootNode().detachChild(mainGeometryNode);
	}
	
	private Spatial loadSkyBox() {
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
		return SkyFactory.createSky(app.getAssetManager(), ft, bk, lt,
				rt, up, dn);
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


}
