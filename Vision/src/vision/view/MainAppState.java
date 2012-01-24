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
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import java.util.List;
import java.util.logging.Logger;

import vision.controller.Controller;
import vision.model.Model;

/**
 * Renders all static objects and rooms
 */
public class MainAppState extends AbstractAppState {
	private AppStateManager stateManager;
	private Controller controller;
	private View app;
	private Model model;
	private Node mainGeometryNode = new Node("static");
	private boolean overviewCam;
	private Logger log = Logger.getLogger(this.getClass().getName());
	private CharacterControl player;
	
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveForward;
	private boolean moveBack;

	
	public MainAppState(Model model, Controller contoller) {
		this.model = model;
		this.overviewCam = false;
		this.controller = contoller;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.stateManager = stateManager;
		this.app = (View) app;
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		if (!isInitialized()) {
			log.severe("Not initialized ");
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
		CapsuleCollisionShape pcs = new CapsuleCollisionShape(0.3f, 3f, 1);
		player = new CharacterControl(pcs, 0.02f);
		player.setFallSpeed(20f);
		player.setGravity(20f);
		player.setJumpSpeed(20);
		player.setPhysicsLocation(new Vector3f(0,50,0));
		pSpace.add(player);
				
		app.getRootNode().attachChild(mainGeometryNode);

		// load skybox
		Spatial sb = loadSkyBox();

		app.getRootNode().attachChild(sb);
		
		app.getRootNode().setCullHint(CullHint.Dynamic);
	
		// init camera
		app.getFlyByCamera().setEnabled(true);
		
		setUpLights();
		setUpKeys();
	}
	
	void setUpLights() {
		//add light
		PointLight lamp_light = new PointLight();
		lamp_light.setColor(ColorRGBA.Yellow);
		lamp_light.setRadius(50f);
		lamp_light.setPosition(new Vector3f(0, 1, 1));
		app.getRootNode().addLight(lamp_light);
		
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		app.getRootNode().addLight(al);
	}

	void setUpKeys() {
		InputManager inputManager = app.getInputManager();
		inputManager.addMapping("select", new KeyTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("zoom", new KeyTrigger(KeyInput.KEY_O));
		inputManager.addListener(controller, new String[] { "zoom", "select" });
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
	    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
	    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
	    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
	    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
	    inputManager.addMapping("toggleMouse", new KeyTrigger(KeyInput.KEY_M));
	    inputManager.addListener(controller, "Left");
	    inputManager.addListener(controller, "Right");
	    inputManager.addListener(controller, "Up");
	    inputManager.addListener(controller, "Down");
	    inputManager.addListener(controller, "Jump");
	    inputManager.addListener(controller, "toggleMouse");
	}
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		Camera cam = app.getCamera();
	    Vector3f camDir = cam.getDirection().clone().multLocal(0.2f);
	    Vector3f camLeft = cam.getLeft().clone().multLocal(0.11f);
	    Vector3f walkDirection = new Vector3f(0, 0, 0);
	    if (moveLeft)  { walkDirection.addLocal(camLeft); }
	    if (moveRight) { walkDirection.addLocal(camLeft.negate()); }
	    if (moveForward)    { walkDirection.addLocal(camDir); }
	    if (moveBack)  { walkDirection.addLocal(camDir.negate()); }
	    player.setWalkDirection(walkDirection);
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


	public void toggleOverviewCam() {
		overviewCam = !overviewCam;
		if (!overviewCam) {
			player.setPhysicsLocation(new Vector3f(0, 10, 0f));
			app.getCamera().lookAt(new Vector3f(1,1,1), new Vector3f(0, 1, 0));
		} else {
			player.setPhysicsLocation(new Vector3f(0, 50, 0));
			app.getCamera().lookAt(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
		}
		player.setEnabled(!overviewCam);
		app.setMouseEnabled(overviewCam);
		
	}

	
	public void userSelect() {
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

	/**
	 * gets called if the user presses or releases a button to move
	 * @param name
	 * @param keyPressed
	 */
	public void userMoveAction(String name, boolean keyPressed) {
		if (name.equals("Left")) {
			moveLeft = keyPressed;
		} else if (name.equals("Right")) {
			moveRight = keyPressed;
		} else if (name.equals("Up")) {
			moveForward = keyPressed;
		} else if (name.equals("Down")) {
 			moveBack = keyPressed;
		} else if (name.equals("Jump")) {
			player.jump();
		}
	}
	
	


}
