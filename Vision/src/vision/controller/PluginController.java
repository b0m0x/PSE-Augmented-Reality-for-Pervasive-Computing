package vision.controller;

import vision.model.Model;


public abstract class PluginController {

	



	/**
	 * @uml.property   name="model"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="pluginController:vision.model.Model"
	 */
	private Model model = new vision.model.Model();

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

}
