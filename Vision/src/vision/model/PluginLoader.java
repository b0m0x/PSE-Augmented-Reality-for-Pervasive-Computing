package vision.model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vision.Config;
import vision.view.Plugin;
import vision.view.View;

/**
 * loads all plugins from a configured subdirectory
 */
public class PluginLoader {

	private List<Plugin> plugins = new ArrayList<Plugin>();

	public List<Plugin> loadPlugins(Model model, View view) {

		List<String> pluginpaths = getPluginPaths();

		for (int i = 0; i < pluginpaths.size(); i++) {

			File fJar = new File(pluginpaths.get(i)); // Path of jar file
			URL url = null;
			try {
				// get Jar-Url
				url = fJar.toURL();
				URLClassLoader urlcl = new URLClassLoader(new URL[] { url });
				String strPackage = "vision.view." + pluginpaths.get(i).substring(0, pluginpaths.get(i).indexOf('.')); // Package/Class-Name
				Class clazz = Class.forName(strPackage, true, urlcl);

				// load Constructor (string, string)
				Constructor cons = null;
				cons = clazz.getConstructor(Model.class, View.class);
				Plugin instance = (Plugin) cons.newInstance(model, view); // call
							
				plugins.add(instance);
			} catch (Exception ex) {
				ex.printStackTrace();
				return Collections.EMPTY_LIST;
			}
		}
		return plugins;
	}

	public List<String> getPluginPaths() {
		List<String> pathlist = new ArrayList<String>();
		File pluginFolder = new File(Config.pluginpath);

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
