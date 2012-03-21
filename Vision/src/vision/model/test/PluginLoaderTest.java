package vision.model.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import vision.model.Model;
import vision.model.PluginLoader;
import vision.view.Plugin;

public class PluginLoaderTest {

	@Test
	public void test() {
		PluginLoader loader = new PluginLoader();
		List<Plugin> plugins = loader.loadPlugins(Model.createModel(null), null);

		assertEquals(false, plugins.isEmpty());
		assertArrayEquals(new String[] { "window" }, plugins.get(0).getTags());

	}

}
