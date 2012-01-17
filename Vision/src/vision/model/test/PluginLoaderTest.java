package vision.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vision.model.Model;
import vision.model.PluginLoader;
import vision.view.HeaterPlugin;
import vision.view.Plugin;
import vision.view.View;

public class PluginLoaderTest {

	@Test
	public void test() {
		PluginLoader loader = new PluginLoader();
		loader.loadPlugins(null, null);
		
	}

}
