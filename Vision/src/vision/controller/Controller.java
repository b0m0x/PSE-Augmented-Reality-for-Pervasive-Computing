package vision.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Collection;

import com.jme3.scene.Geometry;

import vision.model.Model;
import vision.view.View;

/**
 * the main controller that passes key presses, user inputs and events to the
 * subcontrollers and the model
 * 
 */
public class Controller implements ScreenController {

	/**
	 * binds the nifty instance to this controller
	 */
	@Override
	public void bind(Nifty arg0, Screen arg1) {
		// TODO Auto-generated method stub

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
	private Model model = new vision.model.Model();

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
	@NiftyEventSubscriber(pattern = "*button*")
	public void pluginButton(String id) {

	}

	/**
	 * gets called by nifty if a checkbox of a plugin was pressed and forwards
	 * it to the respective plugin controller
	 */
	@NiftyEventSubscriber(pattern = "*checkbox*")
	public void pluginCheckbox(String id) {

	}

	/**
	 * gets called by nifty if a button in the GUI wass pressed
	 * @param id id of the clicked button
	 */
	@NiftyEventSubscriber(pattern = "*button*")
	public void buttonClick(String id) {

	}
	
	/**
	 * called if the user picked an object
	 * @param obj the picked geometry object
	 */
	public void userPick(Geometry obj) {
		
	}

}
