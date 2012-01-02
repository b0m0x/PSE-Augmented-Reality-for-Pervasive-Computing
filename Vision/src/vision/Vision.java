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
		Model mainModel = new Model(mainView);
		Controller mainController = new Controller(mainView, mainModel);
		
		mainView.setController(mainController);
		mainView.setDaten(mainModel);
		
		
	}
	
	
	
}
