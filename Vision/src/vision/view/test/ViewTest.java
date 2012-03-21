package vision.view.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vision.controller.Controller;
import vision.model.Model;
import vision.view.View;

import com.jme3.system.AppSettings;

public class ViewTest {

	@Test
	public void test() {
		AppSettings settings = new AppSettings(true);
		settings.setTitle("UnitTest - Vision");
		settings.setSettingsDialogImage("9.jpg");
		
		View mainView = new View();
		Model mainModel = Model.createModel(mainView);
		Controller mainController = new Controller(mainView, mainModel);
		mainView.setShowSettings(false);
		mainView.setSettings(settings);
		mainView.setController(mainController);
		mainView.setDaten(mainModel);
		
		mainView.setTestModeEnabled(true);
		
		mainView.start();
		
		try {
			synchronized(this) {
				wait(25000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(mainView.getTestSucceeded());
	}

}
