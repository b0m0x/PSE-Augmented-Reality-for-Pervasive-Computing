package vision.model.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.controller.PluginController;
import vision.model.Model;
import vision.model.PluginLoader;
import vision.view.Plugin;

public class PluginLoaderTest {

	@Test
	public void test() {
		PluginLoader loader = new PluginLoader();
		List<Plugin> plugins = null;
		try {
			plugins = loader.loadPlugins(new Model(null), null);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(false, plugins.isEmpty());
		assertArrayEquals(new String[] { "heater" }, plugins.get(0).getTags());

		List<PluginController> controller = loader.getController();

	}

}
