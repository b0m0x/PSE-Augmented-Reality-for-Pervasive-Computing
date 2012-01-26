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
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
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
	
	private Camera miniMapCam;
	private ViewPort miniMapViewPort;
	private Controller controller;
	private View app;
	private Model model;
	private Node mainGeometryNode;
	private boolean overviewCam;
	private Logger log = Logger.getLogger(this.getClass().getName());
	private CharacterControl player;
	
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveForward;
	private boolean moveBack;
	private Node miniMapNode = new Node("Minimap");

	
	public MainAppState(Model model, Controller contoller) {
		this.model = model;
		this.overviewCam = false;
		this.controller = contoller;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app = (View) app;
		
		BulletAppState bas = stateManager.getState(BulletAppState.class);
		PhysicsSpace pSpace = new PhysicsSpace();
		if (bas == null) {
			log.warning("No BulletAppState has been attached - continuing without physics");
		} else {
			pSpace = bas.getPhysicsSpace();
			pSpace.setAccuracy(0.001f);
		}
		
		mainGeometryNode = new Node("static");
		miniMapNode = new Node("minimap");
		List<Spatial> staticObjects = model.getStaticGeometry();

		for (Spatial g : staticObjects) {
			mainGeometryNode.attachChild(g);
			
			//add physics control
			pSpace.add(g.getControl(0));
			
			//we dont want the ceiling in our minimap
			if (!g.getName().equals("ceiling") && !g.getName().equals("floor")) {
				miniMapNode.attachChild(g);
			}
		}
		
		CapsuleCollisionShape pcs = new CapsuleCollisionShape(0.3f, 1.7f, 1);		
		player = new CharacterControl(pcs, 0.02f);
		player.setFallSpeed(20f);
		player.setGravity(20f);
		player.setJumpSpeed(20);
		player.setPhysicsLocation(new Vector3f(4,1,5));
		pSpace.add(player);
				
		this.app.getRootNode().attachChild(mainGeometryNode);
		this.app.getRootNode().attachChild(miniMapNode);
		
		// load skybox
		Spatial sb = loadSkyBox();

		this.app.getRootNode().attachChild(sb);
		
		this.app.getRootNode().setCullHint(CullHint.Dynamic);
	
		// init camera
		this.app.getFlyByCamera().setEnabled(true);
		
		setUpLights();
		setUpKeys();
		
		initMiniMap();
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
	}
	
	private void initMiniMap() {

		miniMapCam = app.getCamera().clone();
		miniMapCam.setViewPort(0.8f, 1.0f, 0.8f, 1.0f);
		miniMapViewPort = app.getRenderManager().createMainView("minimap", miniMapCam);
		miniMapViewPort.setClearFlags(true, true, true);
		miniMapViewPort.attachScene(miniMapNode);
		
		miniMapCam.setLocation(getPlayerPosition().add(new Vector3f(0, 50, 0)));
		miniMapCam.lookAt(getPlayerPosition(), new Vector3f(0, 1, 0));
	}
	
	void setUpLights() {
		//add light
		PointLight lamp_light = new PointLight();
		lamp_light.setColor(ColorRGBA.White);
		lamp_light.setRadius(20f);
		lamp_light.setPosition(new Vector3f(7, 1, 20));
		app.getRootNode().addLight(lamp_light);
		
		//add light
		PointLight lamp_light2 = new PointLight();
		lamp_light2.setColor(ColorRGBA.Green);
		lamp_light2.setRadius(20f);
		lamp_light2.setPosition(new Vector3f(7, 10, 3));
		//app.getRootNode().addLight(lamp_light2);
		miniMapNode.addLight(lamp_light2);
		
		
		PointLight lamp_light3 = new PointLight();
		lamp_light3.setColor(ColorRGBA.White);
		lamp_light3.setRadius(50f);
		lamp_light3.setPosition(new Vector3f(7, 1, 40));
		app.getRootNode().addLight(lamp_light3);
		
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		app.getRootNode().addLight(al);
		
		
	}

	private void setUpKeys() {
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
	    inputManager.addMapping("userPick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
	    
	    inputManager.addListener(controller, "userPick");
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
	    
		app.getCamera().setLocation(player.getPhysicsLocation().addLocal(0, 0.5f, 0));
		
		updateMiniMap();
	}
	
	private void updateMiniMap() {
		miniMapCam.setLocation(player.getPhysicsLocation().add(new Vector3f(0, 50, 0)));
		float[] rot = app.getCamera().getRotation().toAngles(null);
		Quaternion q = new Quaternion().fromAngles(new float[] {(float) (Math.PI / 2), rot[1], 0});
		miniMapCam.setRotation(q);
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		app.getRootNode().detachChild(mainGeometryNode);
	}
	
	private Spatial loadSkyBox() {
		Texture up = app.getAssetManager().loadTexture(
				"Texture/Skybox/up.bmp");
		Texture dn = app.getAssetManager().loadTexture(
				"Texture/Skybox/down.bmp");
		Texture lt = app.getAssetManager().loadTexture(
				"Texture/Skybox/left.bmp");
		Texture rt = app.getAssetManager().loadTexture(
				"Texture/Skybox/right.bmp");
		Texture ft = app.getAssetManager().loadTexture(
				"Texture/Skybox/front.bmp");
		Texture bk = app.getAssetManager().loadTexture(
				"Texture/Skybox/back.bmp");
		return SkyFactory.createSky(app.getAssetManager(), rt, lt, bk,
				ft, up, dn);
	}


	public void toggleOverviewCam() {
		overviewCam = !overviewCam;
		Vector3f playerPos = player.getPhysicsLocation();
		if (!overviewCam) {
			player.setPhysicsLocation(playerPos.add(new Vector3f(0, -50, 0)));
			app.getCamera().setLocation(new Vector3f(4,1,4));
			app.getCamera().lookAt(new Vector3f(1,1,1), new Vector3f(0, 1, 0));
			mainGeometryNode.getChild("ceiling").setCullHint(CullHint.Dynamic);
		} else {
			player.setPhysicsLocation(playerPos.add(new Vector3f(0, 50, 0)));
			app.getCamera().setLocation(player.getPhysicsLocation());
			app.getCamera().lookAt(playerPos.add(0, -50, 0), new Vector3f(0, 1, 0));
			mainGeometryNode.getChild("ceiling").setCullHint(CullHint.Always);
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

	public Vector3f getPlayerPosition() {
		return player.getPhysicsLocation();
	}

	public boolean isInOverview() {
		return overviewCam;		
	}

	/**
	 * gets called if the user selected a position from overview perspective
	 * @param contactPoint the point in global coords
	 */
	public void overviewSelect(Vector3f contactPoint) {
		toggleOverviewCam();
		player.setPhysicsLocation(contactPoint.addLocal(0, 1, 0));		
	}
	
}
