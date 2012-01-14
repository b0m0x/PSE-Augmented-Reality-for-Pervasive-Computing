package vision.model;

import java.util.List;

/**
 * The Sensor class holds all sensor data
 * 
 */
public class Sensor {

	/**
	 * @uml.property name="id"
	 */
	private String id;

	/**
	 * @uml.property name="tags"
	 */
	private List<String> tags;

	/**
	 * @uml.property name="Description"
	 */
	private String description;

	/**
	 * @uml.property name="update"
	 */
	private long update;

	/**
	 * @uml.property name="registered"
	 */
	private long registered;

	/**
	 * @uml.property name="messwert"
	 * @uml.associationEnd inverse="sensor:vision.model.Sample"
	 */
	private List<Sample> messwerte;

	/**
	 * @uml.property name="position"
	 * @uml.associationEnd inverse="sensor:vision.model.Position"
	 */
	private Position position;

	public Sensor(String id, long update, List<Sample> messwerte) {
		setId(id);
		setUpdate(update);
		setMesswerte(messwerte);
	}

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
	 * Getter of the property <tt>registered</tt>
	 * 
	 * @return Returns the registered.
	 * @uml.property name="registered"
	 */
	public long registeredTime() {
		return registered;
	}

	/**
	 * Setter of the property <tt>registered</tt>
	 * 
	 * @param registered
	 *            The registered to set.
	 * @uml.property name="registered"
	 */
	public void setRegistered(long registered) {
		this.registered = registered;
	}

	/**
	 * Getter of the property <tt>messwerte</tt>
	 * 
	 * @return Returns the messwerte.
	 * @uml.property name="messwerte"
	 */
	public List<Sample> getMesswerte() {
		return messwerte;
	}

	/**
	 * sets List of Samples
	 * 
	 * @param messwerte
	 */
	public void setMesswerte(List<Sample> messwerte) {
		this.messwerte = messwerte;
	}
	
	/**
	 * adds Sample to List of samples
	 * 
	 * @param sample
	 */
	public void addSample(Sample sample) {
		this.messwerte.add(sample);
	}

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
