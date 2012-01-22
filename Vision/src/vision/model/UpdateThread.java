package vision.model;

import java.util.Timer;
import java.util.TimerTask;

import vision.Config;

/**
 * updates the sensor data in the background.
 * 
 */
public class UpdateThread extends Thread {

	protected boolean running;

	public UpdateThread() {
		update = new Update();
	}

	public void run() {
		running = true;
		update.getDatabase().connect();

		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				update.store(System.currentTimeMillis());
				if (!running) {
					System.out
							.println("Thread terminated. Closing connection...");
					update.getDatabase().disconnect();
					timer.cancel();
				}
			}
		}, 0, Config.updateIntervall);

	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @uml.property name="update"
	 * @uml.associationEnd inverse="updateThread:vision.model.Update"
	 */
	private Update update;

	/**
	 * Getter of the property <tt>update</tt>
	 * 
	 * @return Returns the update.
	 * @uml.property name="update"
	 */
	public Update getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>
	 * 
	 * @param update
	 *            The update to set.
	 * @uml.property name="update"
	 */
	public void setUpdate(Update update) {
		this.update = update;
	}

}
