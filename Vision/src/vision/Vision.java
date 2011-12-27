package vision;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import vision.controller.Controller;
import vision.controller.PluginController;
import vision.model.Groundplan;
import vision.model.Model;
import vision.model.PluginLoader;
import vision.model.UpdateThread;
import vision.view.Plugin;
import vision.view.View;

/**
 * Main class
 * starts up the whole software 
 *
 */
public class Vision {
	
		

	public static void main(String[] args) {
		
		
		View mainView = new View();
		Model model = new Model(mainView);
		PluginLoader pluginLoader = new PluginLoader();
		UpdateThread updateThread = new UpdateThread();
		Groundplan groundplan = new Groundplan();
		Controller mainController = new Controller();
		
		
		
	}
	
	
	
}
