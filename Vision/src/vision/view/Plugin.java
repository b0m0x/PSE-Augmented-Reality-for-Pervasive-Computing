package vision.view;


import java.util.List;

import vision.model.Model;
import vision.model.Sensor;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;


public abstract class Plugin extends AbstractAppState{

	/**
	 * @uml.property   name="sensors"
	 */
	private List<Sensor> sensors;

	/** 
	 * Getter of the property <tt>sensors</tt>
	 * @return  Returns the sensors.
	 * @uml.property  name="sensors"
	 */
	public List<Sensor> getSensors() {
		return sensors;
	}

	/**
	 * @uml.property  name="tags"
	 */
	private String tags[];

	/**
	 * Getter of the property <tt>tags</tt>
	 * @return  Returns the tags.
	 * @uml.property  name="tags"
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * Setter of the property <tt>tags</tt>
	 * @param tags  The tags to set.
	 * @uml.property  name="tags"
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

		
	/**
	 */
	public Plugin(){
	}

			
	/**
	 */
	protected void clientUpdate(Application application){
	}

				
					
					
	public void update(Application application){
		clientUpdate(application);
	}

						
/**
 */
protected Application getApplication(){
	return null;
	}

/**
 * @uml.property   name="daten"
 * @uml.associationEnd   multiplicity="(1 1)" inverse="plugin:vision.model.Model"
 */
private Model daten = new vision.model.Model(null);

/** 
 * Getter of the property <tt>daten</tt>
 * @return  Returns the daten.
 * @uml.property  name="daten"
 */
public Model getDaten() {
	return daten;
}

/** 
 * Setter of the property <tt>daten</tt>
 * @param daten  The daten to set.
 * @uml.property  name="daten"
 */
public void setDaten(Model daten) {
	this.daten = daten;
}

	
	/**
	 */
	public void initialize(AppStateManager stateManager, Application app){
		this.app = app;
	}

	/**
	 * @uml.property  name="app"
	 */
	private Application app;

	/**
	 * Getter of the property <tt>app</tt>
	 * @return  Returns the app.
	 * @uml.property  name="app"
	 */
	public Application getApp() {
		return app;
	}

	/**
	 * Setter of the property <tt>sensors</tt>
	 * @param sensors  The sensors to set.
	 * @uml.property  name="sensors"
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

}
