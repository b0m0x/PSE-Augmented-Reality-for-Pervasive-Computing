package vision.view;

import java.util.List;

import vision.controller.WindowController;

import com.jme3.app.Application;
import com.jme3.scene.Geometry;

public class WindowPlugin extends Plugin {

	/**
	 * @uml.property name="windows"
	 */
	private List<Geometry> windows;

	/**
	 * Getter of the property <tt>windows</tt>
	 * 
	 * @return Returns the windows.
	 * @uml.property name="windows"
	 */
	public List<Geometry> getWindows() {
		return windows;
	}

	/**
	 * Setter of the property <tt>windows</tt>
	 * 
	 * @param windows
	 *            The windows to set.
	 * @uml.property name="windows"
	 */
	public void setWindows(List<Geometry> windows) {
		this.windows = windows;
	}

	/**
		 */
	protected void clientUpdate(Application application) {
	}

	/**
			 */
	public WindowPlugin() {
	}

	@Override
	protected void clientUpdate(Application application, boolean changed) {
		// TODO Auto-generated method stub
		
	}

}
