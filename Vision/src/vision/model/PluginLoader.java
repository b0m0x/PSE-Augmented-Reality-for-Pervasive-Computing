package vision.model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vision.Config;
import vision.controller.PluginController;
import vision.view.Plugin;
import vision.view.View;

/**
 * loads all plugins from a configured subdirectory
 */
public class PluginLoader {

	private List<Plugin> plugins = new ArrayList<Plugin>();
	private List<PluginController> pluginController = new ArrayList<PluginController>();

	/**
	 * returns the controllers belonging to the plugins.
	 * call loadPlugins before or you will get an empty list
	 * @return a list of plugin controllers, or an empty list if none have been loaded yet
	 */
	public List<PluginController> getController() {
		return pluginController;
	}
	
	/**
	 * loads all plugins from the Config.PLUGIN_PATH path and returns one instance of each
	 * @param model model 
	 * @param view view 
	 * @return a list of plugin instances
	 */
	public List<Plugin> loadPlugins(Model model, View view) {

		List<String> pluginpaths = getPluginPaths();

		for (int i = 0; i < pluginpaths.size(); i++) {

			File fJar = new File(Config.PLUGIN_PATH + File.separator + pluginpaths.get(i)); // Path of jar file
			URL url = null;
			try {
				// get Jar-Url
				url = fJar.toURL();
				URLClassLoader urlcl = new URLClassLoader(new URL[] { url });
				String strPackage = "vision.view."
						+ pluginpaths.get(i).substring(0,
								pluginpaths.get(i).indexOf('.'));
				String strPackageController = "vision.controller."
						+ pluginpaths.get(i).substring(0,
								pluginpaths.get(i).indexOf("Plugin"))
						+ "Controller";
				Class<?> clazz = Class.forName(strPackage, true, urlcl);
				Class<?> clazzController = Class.forName(strPackageController,
						true, urlcl);

				// load Constructor
				Constructor<?> cons = null;
				cons = clazz.getConstructor(Model.class, View.class);
				Plugin instance = (Plugin) cons.newInstance(model, view);

				Constructor<?> consController = null;
				consController = clazzController.getConstructor(Model.class,
						Plugin.class);
				PluginController instanceController = (PluginController) consController
						.newInstance(model, instance);

				plugins.add(instance);
				pluginController.add(instanceController);
			} catch (Exception ex) {
				ex.printStackTrace();
				return Collections.emptyList();
			}
		}
		return plugins;
	}

	private List<String> getPluginPaths() {
		List<String> pathlist = new ArrayList<String>();
		File pluginFolder = new File(Config.PLUGIN_PATH);

		if (!pluginFolder.isDirectory()) {
			return Collections.emptyList();
		}

		// Alle dateien im Root listen...
		for (String file : pluginFolder.list()) {
			pathlist.add(file);
		}

		return pathlist;
	}

}
