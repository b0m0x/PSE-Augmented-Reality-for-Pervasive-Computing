package vision;


import java.util.List;

import com.jme3.app.Application;


public abstract class Plugin {

	/**
	 * @uml.property  name="sensors"
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
 * @uml.associationEnd   inverse="plugin:vision.Model"
 */
private Model daten;

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
}
