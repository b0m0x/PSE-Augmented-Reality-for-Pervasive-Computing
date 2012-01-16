package vision.model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import vision.Config;
import vision.view.Plugin;

/**
 * loads all plugins from a configured subdirectory
 */
public class PluginLoader {

	private List<Plugin> plugins;

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public List<Plugin> getPlugins() {
		return this.plugins;
	}

	public List<Plugin> loadPlugins() {

		List<String> pluginpaths = getPluginPaths();

		for (int i = 0; i < pluginpaths.size(); i++) {

			File fJar = new File(pluginpaths.get(i)); // Path of jar file
			URL url = null;
			try {
				// get Jar-Url
				url = fJar.toURL();
				URLClassLoader urlcl = new URLClassLoader(new URL[] { url });
				String strPackage = "plugin."
						+ pluginpaths.get(i)
								.substring(pluginpaths.lastIndexOf('/'))
								.substring(0, pluginpaths.indexOf('.')); // Package/Class-Name
				Class clazz = Class.forName(strPackage, true, urlcl);

				// load Constructor (string, string)
				Constructor cons = null;
				cons = clazz.getConstructor(String.class, String.class);
				Object instance = cons.newInstance("", ""); // call constructor
				plugins.add((Plugin) instance);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return plugins;
	}

	public List<String> getPluginPaths() {
		List<String> pathlist = null;
		File jarFile = new File(Config.pluginpath);

		// Alle Verzeichnis im Root listen...
		String[] rootDirectories = jarFile.list();
		for (int i = 0; i < rootDirectories.length; i++) {
			pathlist.add(rootDirectories[i]);
		}

		return pathlist;
	}

}
