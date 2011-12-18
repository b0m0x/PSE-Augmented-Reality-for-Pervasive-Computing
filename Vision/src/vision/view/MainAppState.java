package vision.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.Collection;
import vision.model.CustomMesh;

/**
 * Renders all static objects and rooms
 */
public class MainAppState extends AbstractAppState {
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		// TODO Auto-generated method stub
		super.initialize(stateManager, app);
	}

	/**
	 * @uml.property   name="wallMesh"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="mainAppState:vision.model.CustomMesh"
	 */
	private Collection<CustomMesh> wallMesh;

	/**
	 * Getter of the property <tt>wallMesh</tt>
	 * @return  Returns the wallMesh.
	 * @uml.property  name="wallMesh"
	 */
	public Collection<CustomMesh> getWallMesh() {
		return wallMesh;
	}

	/**
	 * Setter of the property <tt>wallMesh</tt>
	 * @param wallMesh  The wallMesh to set.
	 * @uml.property  name="wallMesh"
	 */
	public void setWallMesh(Collection<CustomMesh> wallMesh) {
		this.wallMesh = wallMesh;
	}
}
