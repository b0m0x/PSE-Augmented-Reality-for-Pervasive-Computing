package vision;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Collection;


public abstract class PluginController implements ScreenController {

	
	public class HeaterController extends PluginController {

	}

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
	 * @uml.property  name="model"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="pluginController:vision.Model"
	 */
	private Model model = new vision.Model();

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
	 * @uml.property  name="controller"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="pluginController:vision.Controller"
	 */
	private Collection<Controller> controller;

	/**
	 * Getter of the property <tt>controller</tt>
	 * @return  Returns the controller.
	 * @uml.property  name="controller"
	 */
	public Collection<Controller> getController() {
		return controller;
	}

	/**
	 * Setter of the property <tt>controller</tt>
	 * @param controller  The controller to set.
	 * @uml.property  name="controller"
	 */
	public void setController(Collection<Controller> controller) {
		this.controller = controller;
	}

}
