/**
 * 
 */
package vision.view;

import java.util.List;

import vision.controller.Controller;
import vision.model.Model;

import com.jme3.app.SimpleApplication;

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

	/**
	 * is called every frame by jmonkey
	 */
	public void simpleUpdate() {
	}

	/**
	 * initializes the view
	 */
	public void simpleInitApp() {
		//guiAppState = new vision.view.GuiAppState();
		mainAppState = new vision.view.MainAppState(daten);
		for(Plugin p : daten.getPluginList()) 
		{
			p.initialize(stateManager, this);
			stateManager.attach(p);
		}
		mainAppState.initialize(stateManager, this);
		stateManager.attach(mainAppState);
		//stateManager.attach(guiAppState);
		
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


}
