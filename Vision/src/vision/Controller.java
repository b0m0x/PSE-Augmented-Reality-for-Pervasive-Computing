package vision;

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
	 * @uml.property  name="pluginController"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="controller:vision.PluginController"
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

	/** 
	 * @uml.property name="model"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="controller:vision.Model"
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
	 * @uml.property  name="view"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controller:vision.View"
	 */
	private View view = new vision.View();

	/**
	 * Getter of the property <tt>view</tt>
	 * @return  Returns the view.
	 * @uml.property  name="view"
	 */
	public View getView() {
		return view;
	}

	/**
	 * Setter of the property <tt>view</tt>
	 * @param view  The view to set.
	 * @uml.property  name="view"
	 */
	public void setView(View view) {
		this.view = view;
	}

		
		/**
		 */
		public void pluginButton(String id){
		}

}
