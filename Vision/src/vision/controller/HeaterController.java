package vision.controller;

import vision.view.HeaterPlugin;


public class HeaterController extends PluginController {

	/**
	 * @uml.property   name="heaterPlugin"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="heaterController:vision.view.HeaterPlugin"
	 */
	private HeaterPlugin heaterPlugin = new vision.view.HeaterPlugin();

	/**
	 * Getter of the property <tt>heaterPlugin</tt>
	 * @return  Returns the heaterPlugin.
	 * @uml.property  name="heaterPlugin"
	 */
	public HeaterPlugin getHeaterPlugin() {
		return heaterPlugin;
	}

	/**
	 * Setter of the property <tt>heaterPlugin</tt>
	 * @param heaterPlugin  The heaterPlugin to set.
	 * @uml.property  name="heaterPlugin"
	 */
	public void setHeaterPlugin(HeaterPlugin heaterPlugin) {
		this.heaterPlugin = heaterPlugin;
	}

}
