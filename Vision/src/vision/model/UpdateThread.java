package vision.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import vision.Config;

/**
 *This class updates the sensor data in the background.
 */
public class UpdateThread extends Thread {
    private static final Logger LOG = Logger.getLogger(UpdateThread.class.getName());
	protected boolean running;
	private Model model;
	
	/**
	 * The constructor of the class.
	 * @param model
	 */
	public UpdateThread(Model model) {
		running = true;
		this.model = model;
		update = new Update(model);
	}
	
	/**
	 * 
	 */
	public void run() {
		update.getDatabase().connect(this);
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				update.store(System.currentTimeMillis());
				if (!running) {
					LOG.info("Thread terminated. Closing connection...");
					update.getDatabase().disconnect();
					timer.cancel();
				}
			}
		}, 5000, Config.UPDATE_INTERVAL);

	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}
	/**
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @uml.property name="update"
	 * @uml.associationEnd inverse="updateThread:vision.model.Update"
	 */
	private Update update;

	/**
	 * Getter of the property <tt>update</tt>.
	 * @return Returns the update.
	 * @uml.property name="update"
	 */
	public final Update getUpdate() {
		return update;
	}

	/**
	 * Setter of the property <tt>update</tt>.
	 * @param update
	 *            The update to set.
	 * @uml.property name="update"
	 */
	public final void setUpdate(Update update) {
		this.update = update;
	}

}
