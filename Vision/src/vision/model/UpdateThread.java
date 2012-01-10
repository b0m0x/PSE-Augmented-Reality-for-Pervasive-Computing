package vision.model;

import vision.Config;

/**
 * updates the sensor data in the background.
 * 
 */
public class UpdateThread extends Thread {
	
	public void run() {
		this.update = new Update();
		update.getDaten();
		try {
			wait(Config.updateIntervall);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

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
