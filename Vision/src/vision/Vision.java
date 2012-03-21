package vision;

import vision.controller.Controller;
import vision.model.Model;
import vision.view.View;

import com.jme3.system.AppSettings;

/**
 * Main class starts up the whole software.
 */
public class Vision {

	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setTitle("Vision - Augmented Reality for Pervasive Computing");
		settings.setSettingsDialogImage("9.jpg");
		View mainView = new View();
		Model mainModel = Model.createModel(mainView);
		Controller mainController = new Controller(mainView, mainModel);
		mainView.setSettings(settings);
		mainView.setController(mainController);
		mainView.setDaten(mainModel);
		mainView.start();
	}

}
