package vision.controller.test;


import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.controller.Controller;
import vision.model.Model;
import vision.view.View;
import junit.framework.TestCase;

public class ContollerTest extends TestCase {

	@Test
	public void testContoller() {
		
		MockView view = new MockView();
		Model model = null;
		try {
			model = new Model(view);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		Controller controller = new Controller(view, model);
		
		controller.bind(null, null);
		
		assertEquals(null, controller.getPluginController());
			
	}

}
