package vision.model.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.model.Model;
import vision.model.PluginLoader;
import vision.model.Sensor;
import vision.model.xml.Groundplan;
import vision.view.Plugin;
import vision.view.View;

public class ModelTest {
	@Test
	public void test() throws JAXBException {
		Model model = new Model(null);
		
		model.getTaggedSensors(null);
		
		model.getDatenbank();
		
		List<Sensor> sensor = null;
		model.setSensor(sensor);
		assertEquals(sensor, model.getSensor());
		
		model.getPluginList();
		model.getPluginControllerList();
		
		List<Plugin> pluginList = null;
		model.setPluginList(pluginList);
		assertEquals(pluginList, model.getPluginList());
		
		Groundplan groundplan = null;
		model.setGroundplan(groundplan);
		assertEquals(groundplan, model.getGroundplan());
		
		View view = null;
		model.setView(view);
		assertEquals(view, model.getView());
		
		PluginLoader pluginLoader = null;
		model.setPluginLoader(pluginLoader);
		assertEquals(pluginLoader, model.getPluginLoader());
		
	}
}
