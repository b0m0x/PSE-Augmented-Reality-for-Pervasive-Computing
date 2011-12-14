package vision.model;

/**
 * updates the sensor data in the background.
 * 
 */
public class UpdateThread extends Thread {

	/**
	 * @uml.property  name="update"
	 * @uml.associationEnd  inverse="updateThread:vision.model.Update"
	 */
	private Update update;

	/**
	 * Getter of the property <tt>update</tt>
	 * @return  Returns the update.
	 * @uml.property  name="update"
	 */
	public Update getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * @param update  The update to set.
	 * @uml.property  name="update"
	 */
	public void setUpdate(Update update) {
		this.update = update;
	}

}
