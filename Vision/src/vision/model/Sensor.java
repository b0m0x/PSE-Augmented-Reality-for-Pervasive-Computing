package vision.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Sensor class holds all sensor data
 * 
 */
public class Sensor {
	
	public Sensor() {
		this.setMesswert();
	}

	/**
	 * @uml.property name="id"
	 */
	private String id;

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @uml.property name="tags"
	 */
	private List<String> tags;

	/**
	 * Getter of the property <tt>tags</tt>
	 * 
	 * @return Returns the tags.
	 * @uml.property name="tags"
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Setter of the property <tt>tags</tt>
	 * 
	 * @param tags
	 *            The tags to set.
	 * @uml.property name="tags"
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @uml.property name="Description"
	 */
	private String description;

	/**
	 * Getter of the property <tt>Description</tt>
	 * 
	 * @return Returns the description.
	 * @uml.property name="Description"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>Description</tt>
	 * 
	 * @param Description
	 *            The description to set.
	 * @uml.property name="Description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @uml.property name="update"
	 */
	private long update;

	/**
	 * Getter of the property <tt>update</tt>
	 * 
	 * @return Returns the update.
	 * @uml.property name="update"
	 */
	public long getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * 
	 * @param update
	 *            The update to set.
	 * @uml.property name="update"
	 */
	public void setUpdate(long update) {
		this.update = update;
	}

	/**
	 * @uml.property name="registered"
	 */
	private boolean registered;

	/**
	 * Getter of the property <tt>registered</tt>
	 * 
	 * @return Returns the registered.
	 * @uml.property name="registered"
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * Setter of the property <tt>registered</tt>
	 * 
	 * @param registered
	 *            The registered to set.
	 * @uml.property name="registered"
	 */
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	/**
	 * @uml.property name="messwert"
	 * @uml.associationEnd inverse="sensor:vision.model.Sample"
	 */
	private List<Sample> messwert;

	/**
	 * Getter of the property <tt>messwert</tt>
	 * 
	 * @return Returns the messwert.
	 * @uml.property name="messwert"
	 */
	public List<Sample> getMesswert() {
		return messwert;
	}
	
	private void setMesswert() {
		this.messwert = new ArrayList<Sample>();
	}
	
	public void addToList(Sample s) {
		this.messwert.add(s);
	}

	/**
	 * Setter of the property <tt>messwert</tt>
	 * 
	 * @param messwert
	 *            The messwert to set.
	 * @uml.property name="messwert"
	 */


	/**
	 * @uml.property name="position"
	 * @uml.associationEnd inverse="sensor:vision.model.Position"
	 */
	private Position position;

	/**
	 * Getter of the property <tt>position</tt>
	 * 
	 * @return Returns the position.
	 * @uml.property name="position"
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Setter of the property <tt>position</tt>
	 * 
	 * @param position
	 *            The position to set.
	 * @uml.property name="position"
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

}
