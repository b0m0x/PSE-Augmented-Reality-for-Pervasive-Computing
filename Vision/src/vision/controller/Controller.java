package vision.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Collection;


public class Controller implements ScreenController {

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
	 * @uml.property   name="pluginController"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="controller:vision.controller.PluginController"
	 */
	private Collection<PluginController> pluginController;

	/**
	 * Getter of the property <tt>pluginController</tt>
	 * @return  Returns the pluginController.
	 * @uml.property  name="pluginController"
	 */
	public Collection<PluginController> getPluginController() {
		return pluginController;
	}

	/**
	 * Setter of the property <tt>pluginController</tt>
	 * @param pluginController  The pluginController to set.
	 * @uml.property  name="pluginController"
	 */
	public void setPluginController(
			Collection<PluginController> pluginController) {
				this.pluginController = pluginController;
			}

}
