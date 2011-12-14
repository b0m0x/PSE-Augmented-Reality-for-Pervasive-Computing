package vision.view;

import java.util.List;

import vision.controller.WindowController;
import vision.view.Plugin;

import com.jme3.app.Application;
import com.jme3.scene.Geometry;


public class WindowPlugin extends Plugin {

	/**
	 * @uml.property  name="windows"
	 */
	private List<Geometry> windows;

	/**
	 * Getter of the property <tt>windows</tt>
	 * @return  Returns the windows.
	 * @uml.property  name="windows"
	 */
	public List<Geometry> getWindows() {
		return windows;
	}

	/**
	 * Setter of the property <tt>windows</tt>
	 * @param windows  The windows to set.
	 * @uml.property  name="windows"
	 */
	public void setWindows(List<Geometry> windows) {
		this.windows = windows;
	}

		
		/**
		 */
		protected void clientUpdate(Application application){
		}

			
			/**
			 */
			public WindowPlugin(){
			}


			/**
			 * @uml.property   name="windowController"
			 * @uml.associationEnd   multiplicity="(1 1)" inverse="windowPlugin:vision.controller.WindowController"
			 */
			private WindowController windowController = new vision.controller.WindowController();

			/**
			 * Getter of the property <tt>windowController</tt>
			 * @return  Returns the windowController.
			 * @uml.property  name="windowController"
			 */
			public WindowController getWindowController() {
				return windowController;
			}

			/**
			 * Setter of the property <tt>windowController</tt>
			 * @param windowController  The windowController to set.
			 * @uml.property  name="windowController"
			 */
			public void setWindowController(WindowController windowController) {
				this.windowController = windowController;
			}

}
