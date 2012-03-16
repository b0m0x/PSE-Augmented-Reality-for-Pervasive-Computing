package vision.model.test;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.model.Model;

public class ModelTest {
	@Test
	public void test() throws JAXBException {
		Model model = new Model(null);
		
		model.getTaggedSensors(null);
		model.getSensor();
		model.getDatenbank();
		
		model.setSensor(null);
		
		model.getPluginList();
		model.getPluginControllerList();
		
		model.setPluginList(null);
		model.getGroundplan();
		
		model.setGroundplan(null);
		model.getView();
		
		
	}
}
