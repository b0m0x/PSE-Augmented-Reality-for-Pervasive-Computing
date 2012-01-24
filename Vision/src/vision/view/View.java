/**
 * 
 */
package vision.view;



import java.util.logging.Logger;

import vision.controller.Controller;
import vision.model.Model;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;

/**
 * main class of the view package. contains the main update loop and calls the
 * plugin and main views
 * 
 */
public class View extends SimpleApplication {
	Camera miniMapCam;
	/**
	 * @uml.property name="daten"
	 * @uml.associationEnd inverse="view:vision.model.Model"
	 */
	private Model daten;

	/**
	 * @uml.property name="controller"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="view:vision.controller.Controller"
	 */
	private Controller controller;

	/**
	 * @uml.property name="guiAppState"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="view:vision.view.GuiAppState"
	 */
	private GuiAppState guiAppState;

	/**
	 * @uml.property name="mainAppState"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="view:vision.view.MainAppState"
	 */
	private MainAppState mainAppState;
	
	private BulletAppState bulletAppState;

	private boolean showMouse;
	private ViewPort miniMapViewPort;

	/**
	 * is called every frame by jmonkey
	 */
	@Override
	public void simpleUpdate(float tpf) {
		miniMapCam.setLocation(mainAppState.getPlayerPosition().add(new Vector3f(0, 50, 0)));
		float[] rot = cam.getRotation().toAngles(null);
		Quaternion q = new Quaternion().fromAngles(new float[] {(float) (Math.PI / 2), rot[1], 0});
		miniMapCam.setRotation(q);
		
	}

	/**
	 * initializes the view
	 */
	public void simpleInitApp() {

		guiAppState = new GuiAppState(controller);
		bulletAppState = new BulletAppState();
		mainAppState = new MainAppState(daten, controller);
		

		guiAppState.initialize(stateManager, this);
		mainAppState.initialize(stateManager, this);
	
		stateManager.attach(guiAppState);
		stateManager.attach(bulletAppState);
		stateManager.attach(mainAppState);
		

		for (Plugin p : daten.getPluginList()) {
			stateManager.attach(p);
		}
		
		initMiniMap();
	}
	
	private void initMiniMap() {
		miniMapCam = cam.clone();
		miniMapCam.setViewPort(0.8f, 1.0f, 0.8f, 1.0f);
		miniMapViewPort = renderManager.createMainView("minimap", miniMapCam);
		miniMapViewPort.setClearFlags(true, true, true);
		miniMapViewPort.attachScene(rootNode);
		
		
		miniMapCam.setLocation(mainAppState.getPlayerPosition().add(new Vector3f(0, 50, 0)));
		miniMapCam.lookAt(mainAppState.getPlayerPosition(), new Vector3f(0, 1, 0));
	}

	/**
	 * Getter of the property <tt>daten</tt>
	 * 
	 * @return Returns the daten.
	 * @uml.property name="daten"
	 */
	public Model getDaten() {
		return daten;
	}

	/**
	 * Setter of the property <tt>daten</tt>
	 * 
	 * @param daten
	 *            The daten to set.
	 * @uml.property name="daten"
	 */
	public void setDaten(Model daten) {
		this.daten = daten;
	}

	/**
	 * Getter of the property <tt>controller</tt>
	 * 
	 * @return Returns the controller.
	 * @uml.property name="controller"
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Setter of the property <tt>controller</tt>
	 * 
	 * @param controller
	 *            The controller to set.
	 * @uml.property name="controller"
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Getter of the property <tt>mainAppState</tt>
	 * 
	 * @return Returns the mainAppState.
	 * @uml.property name="mainAppState"
	 */
	public MainAppState getMainAppState() {
		return mainAppState;
	}

	/**
	 * Setter of the property <tt>mainAppState</tt>
	 * 
	 * @param mainAppState
	 *            The mainAppState to set.
	 * @uml.property name="mainAppState"
	 */
	public void setMainAppState(MainAppState mainAppState) {
		this.mainAppState = mainAppState;
	}

	/**
	 * Getter of the property <tt>guiAppState</tt>
	 * 
	 * @return Returns the guiAppState.
	 * @uml.property name="guiAppState"
	 */
	public GuiAppState getGuiAppState() {
		return guiAppState;
	}

	/**
	 * Setter of the property <tt>guiAppState</tt>
	 * 
	 * @param guiAppState
	 *            The guiAppState to set.
	 * @uml.property name="guiAppState"
	 */
	public void setGuiAppState(GuiAppState guiAppState) {
		this.guiAppState = guiAppState;
	}

	/**
	 * enables a plugin
	 * 
	 * @param p
	 *            the plugin to enable
	 */
	public void enablePlugin(Plugin p) {
		stateManager.attach(p);
	}

	/**
	 * disables a plugin
	 * 
	 * @param p
	 *            the plugin to detach
	 */
	public void disablePlugin(Plugin p) {
		stateManager.detach(p);
	}

	public void toggleOverviewCam() {
		mainAppState.toggleOverviewCam();		
	}

	public void userSelect() {
		mainAppState.userSelect();
	}

	public void userMoveAction(String name, boolean keyPressed) {
		mainAppState.userMoveAction(name, keyPressed);		
	}

	public void toggleMouse() {
		showMouse = !showMouse;
		setMouseEnabled(showMouse);
	}
	
	
	public void setMouseEnabled(boolean enabled) {
		inputManager.setCursorVisible(enabled);
		flyCam.setEnabled(!enabled);
		showMouse = enabled;
	}
	
	private void updateMiniMap() {
		
	}

}
