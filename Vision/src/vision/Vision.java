package vision;

import javax.xml.bind.JAXBException;

import com.jme3.system.AppSettings;

import vision.controller.Controller;

import vision.model.Model;

import vision.view.View;

/**
 * Main class starts up the whole software
 * 
 */
public class Vision {

	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setSettingsDialogImage("8.png");
		settings.setTitle("Vision - Augmented Reality for Pervasive Computing");
		View mainView = new View();
		Model mainModel = null;
		try {
			mainModel = new Model(mainView);
		} catch (JAXBException e) {
			e.printStackTrace();
			return;
		}
		Controller mainController = new Controller(mainView, mainModel);
		mainView.setSettings(settings);
		mainView.setController(mainController);
		mainView.setDaten(mainModel);
		
		mainView.start();
	}

}
