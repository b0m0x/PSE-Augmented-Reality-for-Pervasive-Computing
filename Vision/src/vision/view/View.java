/**
 * 
 */
package vision.view;



import java.util.logging.Logger;

import vision.controller.Controller;
import vision.model.Model;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;

import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;

/**
 * main class of the view package. contains the main update loop and calls the
 * plugin and main views
 */
public class View extends SimpleApplication {
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

	private BloomFilter bloomFilter;

	private FilterPostProcessor fpp;

	private boolean testMode;

	private boolean testSuccessful;



	/**
	 * initializes the view
	 */
	public void simpleInitApp() {

		guiAppState = new GuiAppState(controller, daten);
		bulletAppState = new BulletAppState();
		mainAppState = new MainAppState(daten, controller);

		stateManager.attach(guiAppState);
		stateManager.attach(bulletAppState);
		stateManager.attach(mainAppState);

		for (Plugin p : daten.getPluginList()) {
			stateManager.attach(p);
		}
		setUpCam();
		setUpPostProcessingEffects();
	}


	private void setUpPostProcessingEffects() {

        fpp = new FilterPostProcessor(assetManager);

        bloomFilter = new BloomFilter();
        bloomFilter.setBlurScale(1.4f);
        bloomFilter.setBloomIntensity(0.1f);
        bloomFilter.setExposureCutOff(0.01f);
        bloomFilter.setExposurePower(2.f);
        bloomFilter.setDownSamplingFactor(2f);
        fpp.addFilter(bloomFilter);
        viewPort.addProcessor(fpp);

	}




	private void setUpCam() {
		cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, 1000f);
	}

	/**
	 * Getter of the property <tt>daten</tt>.
	 * @return Returns the daten.
	 * @uml.property name="daten"
	 */
	public Model getDaten() {
		return daten;
	}

	/**
	 * Setter of the property <tt>daten</tt>.
	 * @param daten
	 *            The daten to set.
	 * @uml.property name="daten"
	 */
	public void setDaten(Model daten) {
		this.daten = daten;
	}

	/**
	 * Getter of the property <tt>controller</tt>
	 * 	 * @return Returns the controller.
	 * @uml.property name="controller"
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Setter of the property <tt>controller</tt>
	 * @param controller
	 *            The controller to set.
	 * @uml.property name="controller"
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Getter of the property <tt>mainAppState</tt>
	 * @return Returns the mainAppState.
	 * @uml.property name="mainAppState"
	 */
	public MainAppState getMainAppState() {
		return mainAppState;
	}

	/**
	 * Setter of the property <tt>mainAppState</tt>
	 * @param mainAppState
	 *            The mainAppState to set.
	 * @uml.property name="mainAppState"
	 */
	public void setMainAppState(MainAppState mainAppState) {
		this.mainAppState = mainAppState;
	}

	/**
	 * Getter of the property <tt>guiAppState</tt>.
	 * @return Returns the guiAppState.
	 * @uml.property name="guiAppState"
	 */
	public GuiAppState getGuiAppState() {
		return guiAppState;
	}

	/**
	 * Setter of the property <tt>guiAppState</tt>.
	 * @param guiAppState
	 *            The guiAppState to set.
	 * @uml.property name="guiAppState"
	 */
	public final void setGuiAppState(GuiAppState guiAppState) {
		this.guiAppState = guiAppState;
	}

	/**
	 * enables a plugin
	 * @param p
	 *            the plugin to enable
	 */
	public final void enablePlugin(Plugin plugin) {
		stateManager.attach(plugin);
	}

	/**
	 * disables a plugin
	 * @param p
	 *            the plugin to detach
	 */
	public final void disablePlugin(Plugin plugin) {
		stateManager.detach(plugin);
	}
	/**
	 * 
	 */
	public void toggleOverviewCam() {
		mainAppState.toggleOverviewCam();
	}
	/**
	 * 
	 */
	public void userSelect() {
		mainAppState.userSelect();
	}
	/**
	 * 
	 * @param name
	 * @param keyPressed
	 */
	public void userMoveAction(String name, boolean keyPressed) {
		mainAppState.userMoveAction(name, keyPressed);		
	}
	/**
	 * 
	 */
	public void toggleMouse() {
		showMouse = !showMouse;
		setMouseEnabled(showMouse);
	}
	
	/**
	 * 
	 * @param enabled
	 */
	public void setMouseEnabled(boolean enabled) {
		inputManager.setCursorVisible(enabled);
		flyCam.setEnabled(!enabled);
		showMouse = enabled;
	}

	public boolean isInOverview() {
		return mainAppState.isInOverview();
	}
	
	private int testIteration;
	
	void localAssert(boolean condition) {
		if (condition) {
			return;
		}		
		testMode = false;
		stop();
		throw new AssertionError();
	}
	
	/** 
	 * update loop called every frame by jmonkey
	 * only used for automatic testing
	 */	
	@Override
	public void update() {
		super.update();
		if (!testMode) {
			return;
		}
		switch (testIteration) {
			case 20:
				userSelect();
				break;
			case 50:
				localAssert(!isInOverview());
				toggleMouse();
				toggleOverviewCam();
				break;
			case 100:
				localAssert(isInOverview());
				localAssert(inputManager.isCursorVisible());
				break;
			case 150:				
				toggleMouse();
				localAssert(!inputManager.isCursorVisible());
				break;
			case 200:
				controller.onAction("Left", false, 20);
				controller.onAction("Right", false, 20);
				controller.onAction("Up", false, 20);
				controller.onAction("Down", false, 20);
				controller.onAction("Jump", false, 20);
				controller.onAction("fooanfqirflajnsoinqeflkn", false, 20);
				controller.onAction("toggleMouse", false, 20);
				localAssert(inputManager.isCursorVisible());
				controller.onAction("toggleMouse", false, 20);
				localAssert(!inputManager.isCursorVisible());
							
				
				//DIsable heater and WIndow plugin
				controller.plugincheckboxPressed("Pluginchecbox_vision.view.HeaterPlugin", new CheckBoxStateChangedEvent(null, false));
				controller.plugincheckboxPressed("Pluginchecbox_vision.view.WindowPlugin", new CheckBoxStateChangedEvent(null, false));
				
				localAssert(stateManager.getState(HeaterPlugin.class) == null);
				localAssert(stateManager.getState(WindowPlugin.class) == null);
				guiAppState.managePluginsPopupMenu();
				break;
			case 250:
				controller.plugincheckboxPressed("Pluginchecbox_vision.view.HeaterPlugin", new CheckBoxStateChangedEvent(null, true));
				controller.plugincheckboxPressed("Pluginchecbox_vision.view.WindowPlugin", new CheckBoxStateChangedEvent(null, true));
				
				localAssert(stateManager.getState(HeaterPlugin.class) != null);
				localAssert(stateManager.getState(WindowPlugin.class) != null);
				
				userPick();
				controller.backToMainScreen("la", null);
			case 1000:
				testSuccessful = true;
				stop();
				break;
				
		}
		testIteration++;		
	}

	public void userPick() {
		CollisionResults results = new CollisionResults();		
		

        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
	
        Ray ray = new Ray(click3d, dir);
        
        rootNode.collideWith(ray, results);
        
        for (CollisionResult r : results) {
        	if (r.getGeometry().getName().equals("floor") && isInOverview()) {
        		mainAppState.overviewSelect(r.getContactPoint());
        		return;
        	}
        	if (r.getGeometry().getName().contains("id")) {
        		String id = r.getGeometry().getName().substring(0, r.getGeometry().getName().indexOf("id"));
        		guiAppState.showState(id);
        	}
        }
	}
	
	@Override
	public void stop() {
		daten.close();
		super.stop();
	}
	
	/**
	 * enables or disables the bloom effect
	 */
	public void enablePostProcessingEffects(boolean enable) {
		bloomFilter.setEnabled(enable);
	}


	/**
	 * enables automatic testing
	 * @param b
	 */
	public void setTestModeEnabled(boolean b) {
		testMode = b;
	}

	public boolean getTestSucceeded() {
		return testSuccessful;
	}
}
