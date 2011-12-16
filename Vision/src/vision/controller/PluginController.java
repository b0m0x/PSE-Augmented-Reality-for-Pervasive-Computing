package vision.controller;
import java.util.Map;

import de.lessvoid.nifty.NiftyEventSubscriber;

import vision.model.Model;
import vision.view.Plugin;

/**
 * abstract superclass for all plugin controllers
 * 
 *
 */
public abstract class PluginController {

	/**
	 * constructs a new PluginController
	 * @param model the model
	 * @param plugin the plugin to manage
	 */
	public PluginController(Model model, Plugin plugin) {
		this.model = model;
		this.plugin = plugin;
	}


	/**
	 * @uml.property   name="model"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="pluginController:vision.model.Model"
	 */
	private Model model;
	private Plugin plugin;

	/**
	 * Getter of the property <tt>model</tt>
	 * @return  Returns the model.
	 * @uml.property  name="model"
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Setter of the property <tt>model</tt>
	 * @param model  The model to set.
	 * @uml.property  name="model"
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * callback function that gets called by the main controller if the user clicks on a plugin buttons
	 * @param id the id of the button
	 */
	public void buttonPressed(String id) {
		
	}
	
	/**
	 * returns a List of plugin-defined buttons that the main system creates for the plugin
	 * @return a Map of button ids and their Text
	 */
	public Map<String, String> createButtons() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * returns a List of plugin-defined checkboxes (options) that the main system creates for the plugin
	 * @return a Map of checkbox ids and their texts
	 */
	public Map<String, String> createCheckBoxes() {
		throw new UnsupportedOperationException();
	}


	/**
	 * @uml.property  name="plugin1"
	 * @uml.associationEnd  inverse="pluginController:vision.view.Plugin"
	 */
	private Plugin plugin1;

	/**
	 * Getter of the property <tt>plugin1</tt>
	 * @return  Returns the plugin1.
	 * @uml.property  name="plugin1"
	 */
	public Plugin getPlugin1() {
		return plugin1;
	}

	/**
	 * Setter of the property <tt>plugin1</tt>
	 * @param plugin1  The plugin1 to set.
	 * @uml.property  name="plugin1"
	 */
	public void setPlugin1(Plugin plugin1) {
		this.plugin1 = plugin1;
	}

}
