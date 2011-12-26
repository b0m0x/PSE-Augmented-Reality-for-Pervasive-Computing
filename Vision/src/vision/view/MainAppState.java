package vision.view;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.Collection;
import java.util.List;

import vision.model.CustomMesh;
import vision.model.Model;

/**
 * Renders all static objects and rooms
 */
public class MainAppState extends AbstractAppState {
	private AppStateManager stateManager;
	private SimpleApplication app;
	private Model model;
	private Node mainGeometryNode = new Node("static");
	
	public MainAppState(Model model) {
		this.model = model;
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.stateManager = stateManager;
		this.app = (SimpleApplication) app;
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		List<Geometry> staticObjects = model.getStaticGeometry();
		for (Geometry g : staticObjects) {
			mainGeometryNode.attachChild(g);
		}
		app.getRootNode().attachChild(mainGeometryNode);
		
		//TODO: init camera
	}
	
	@Override
	public void stateDetached(AppStateManager stateManager) {
		super.stateDetached(stateManager);
		app.getRootNode().detachChild(mainGeometryNode);
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
