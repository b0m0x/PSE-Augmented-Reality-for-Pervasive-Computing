package vision.controller.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import vision.controller.HeaterController;
import vision.controller.WindowController;
import vision.model.Model;
import vision.view.HeaterPlugin;
import vision.view.View;
import vision.view.WindowPlugin;

public class PluginContollerTest {

	@Test
	public void testHeaterController() {
		View view = new View();
		Model model = Model.createModel(view);
		HeaterPlugin heaterPlugin = new HeaterPlugin(model, view);
		HeaterController heaterController = new HeaterController(model,
				heaterPlugin);
		
		heaterController.createButtons();
		heaterController.buttonPressed("test press");

		assertNotNull(heaterController);
	}

	@Test
	public void testWindowController() {
		View view = new View();
		Model model = Model.createModel(view);
		WindowPlugin windowPlugin = new WindowPlugin(model, view);
		WindowController windowController = new WindowController(model,
				windowPlugin);

		assertNotNull(windowController);
	}

}
