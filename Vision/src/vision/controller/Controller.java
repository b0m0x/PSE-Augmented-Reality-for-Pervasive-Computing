package vision.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.checkbox.CheckboxControl;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;

import java.awt.peer.ButtonPeer;
import java.util.Collection;
import java.util.logging.Logger;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.scene.Geometry;

import vision.model.Model;
import vision.view.Plugin;
import vision.view.View;

/**
 * the main controller that passes key presses, user inputs and events to the
 * subcontrollers and the model
 * 
 */
public class Controller implements ScreenController, ActionListener, AnalogListener  {
	
	private Nifty nifty;
	private Screen screen;
	private final static Logger LOG = Logger.getLogger(Controller.class.getName());
	
	/**
	 * Constructs a new Controller.
	 * @param view the view
	 * @param model the model
	 */
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}


	/**
	 * binds the nifty instance to this controller
	 */
	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}

	/**
	 * @uml.property name="pluginController"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 *                     inverse="controller:vision.controller.PluginController"
	 */
	private Collection<PluginController> pluginController;

	/**
	 * Getter of the property <tt>pluginController</tt>
	 * 
	 * @return Returns the pluginController.
	 * @uml.property name="pluginController"
	 */
	public Collection<PluginController> getPluginController() {
		return pluginController;
	}

	/**
	 * Setter of the property <tt>pluginController</tt>
	 * 
	 * @param pluginController
	 *            The pluginController to set.
	 * @uml.property name="pluginController"
	 */
	public void setPluginController(
			Collection<PluginController> pluginController) {
		this.pluginController = pluginController;
	}

	/**
	 * @uml.property name="model"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="controller:vision.model.Model"
	 */
	private Model model;

	/**
	 * Getter of the property <tt>model</tt>
	 * 
	 * @return Returns the model.
	 * @uml.property name="model"
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Setter of the property <tt>model</tt>
	 * 
	 * @param model
	 *            The model to set.
	 * @uml.property name="model"
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * @uml.property name="view"
	 * @uml.associationEnd multiplicity="(1 1)"
	 *                     inverse="controller:vision.view.View"
	 */
	private View view = new vision.view.View();

	/**
	 * Getter of the property <tt>view</tt>
	 * 
	 * @return Returns the view.
	 * @uml.property name="view"
	 */
	public View getView() {
		return view;
	}

	/**
	 * Setter of the property <tt>view</tt>
	 * 
	 * @param view
	 *            The view to set.
	 * @uml.property name="view"
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * pluginButton gets called by nifty if a button of a plugin was pressed and
	 * forwards it to the respective plugin controller
	 */
	@NiftyEventSubscriber(pattern = "ButtonOf_.*")
	public void pluginButton(String id, ButtonClickedEvent bce) {
		String s = id.substring(9);
		for (PluginController p : model.getPluginControllerList()) {
			if (s.startsWith(p.getClass().getName())) {
				p.buttonPressed(id);
			}
		}

	}

	

	/**
	 * gets called by nifty if a button in the GUI was pressed
	 * 
	 * @param id
	 *            id of the clicked button
	 */
	//@NiftyEventSubscriber(pattern = ".*button.*")
	public void buttonClick(String id, ButtonClickedEvent bce) {
		Logger l= Logger.getLogger("buttonclick");
		l.info("Button was pressed.");
	}

	
		
	private boolean loaded;
	
	/**
	 * gets called if the user pressed the activate/deactivate button
	 */
	@NiftyEventSubscriber(id = "btn_manageplugins")
	public void createManagePluginsPopupMenu(String id, ButtonClickedEvent bce) {
	
		view.getGuiAppState().managePluginsPopupMenu();
	}
	
	/**
	 * 
	 * @param id
	 * @param o
	 */
	@NiftyEventSubscriber(id = "btn_Help")
	public void help(String id, ButtonClickedEvent bce) {
		Logger l= Logger.getLogger("buttonclick");
		l.info("Button was pressed.");
	}
	
	
	
	

	/**
	 * 
	 * @param id
	 * @param o
	 */
	@NiftyEventSubscriber(id = "btn_Settings")
	public void settings(String id, ButtonClickedEvent bce) {
		nifty.gotoScreen("options");
	}
	
	
	/**
	 * 
	 * @param id
	 * @param o
	 */
	@NiftyEventSubscriber(id = "btn_Overview")
	public void toggleOverview(String id, ButtonClickedEvent o) {
		view.toggleOverviewCam();
		Logger.getLogger("here").warning("click");
	}

	@NiftyEventSubscriber(id = "btn_back")
	public void backToMainScreen(String id, ButtonClickedEvent bce) {
		nifty.gotoScreen("start");
		
	}
	

	/**
	 * gets called if a user checked or unchecked a non-plugin defined checkbox
	 * 
	 * @param id
	 *            the id of the checkbox that was pressed
	 */
	@NiftyEventSubscriber(pattern = "Pluginchecbox_.*")
	public void plugincheckboxPressed(String id, CheckBoxStateChangedEvent cbsce) {
			String s = id.substring(14);
			for(Plugin p : model.getPluginList()) {
				if (s.equals(p.getClass().getName())) {
					if(cbsce.isChecked() == true) {
						view.enablePlugin(p);
					} 
					else {
						view.disablePlugin(p);
					}
					
				}
			}
	}


	
	
	@Override
	public void onAction(String name, boolean keyPressed, float tpf) {
		if (name.equals("zoom") && keyPressed) {
			view.toggleOverviewCam();
		} else if (name.equals("Left") || name.equals("Right") || name.equals("Up") || name.equals("Down") || name.equals("Jump")) {
			view.userMoveAction(name, keyPressed);
		} else if (name.equals("toggleMouse") && !keyPressed) {
			view.toggleMouse();
		}

	}

	@Override
	public void onAnalog(String name, float intensity, float tpf) {
		if (name.equals("userPick")) {
			view.userPick();
		}
		
	}
	

}
