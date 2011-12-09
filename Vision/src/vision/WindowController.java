package vision;


public class WindowController extends PluginController {

	/**
	 * @uml.property  name="windowPlugin"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="windowController:vision.WindowPlugin"
	 */
	private WindowPlugin windowPlugin = new vision.WindowPlugin();

	/**
	 * Getter of the property <tt>windowPlugin</tt>
	 * @return  Returns the windowPlugin.
	 * @uml.property  name="windowPlugin"
	 */
	public WindowPlugin getWindowPlugin() {
		return windowPlugin;
	}

	/**
	 * Setter of the property <tt>windowPlugin</tt>
	 * @param windowPlugin  The windowPlugin to set.
	 * @uml.property  name="windowPlugin"
	 */
	public void setWindowPlugin(WindowPlugin windowPlugin) {
		this.windowPlugin = windowPlugin;
	}

}
