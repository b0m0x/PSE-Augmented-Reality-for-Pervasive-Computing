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
import com.jme3.input.FlyByCamera;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;

/**
 * main class of the view package. contains the main update loop and calls the
 * plugin and main views
 * 
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

	private DepthOfFieldFilter dofFilter;

	private FilterPostProcessor fpp;



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
        //     fpp.setNumSamples(4);

        dofFilter = new DepthOfFieldFilter();
        dofFilter.setFocusDistance(0);
        dofFilter.setFocusRange(20);
        dofFilter.setBlurScale(1.4f);
        fpp.addFilter(dofFilter);
        viewPort.addProcessor(fpp);
		
	}




	private void setUpCam() {
		cam.setFrustumPerspective(45f, (float) cam.getWidth() / cam.getHeight(), 0.01f, 1000f);
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
	public void enablePlugin(Plugin plugin) {
		stateManager.attach(plugin);
	}

	/**
	 * disables a plugin
	 * 
	 * @param p
	 *            the plugin to detach
	 */
	public void disablePlugin(Plugin plugin) {
		stateManager.detach(plugin);
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

	public boolean isInOverview() {
		return mainAppState.isInOverview();
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
	
}
