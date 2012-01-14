package vision.model;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import vision.view.Plugin;

/**
 * loads all plugins from a configured subdirectory
 */
public class PluginLoader {

	public List<Plugin> loadPlugins(){
		
		 File fJar = new File(JarFile); //Path of jar file
	     URL url = null;
	     try {
	         //get Jar-Url
	         url = fJar.toURL();
	         URLClassLoader urlcl = new URLClassLoader(new URL[] {url});
	         String strPackage = "abc.moep"; //Package/Class-Name
	         Class clazz = Class.forName(strPackage, true, urlcl);
	      
	         //load Constructor (string, string)
	         Constructor cons = null;
	         cons = clazz.getConstructor(String.class, String.class);
	         Object instance = cons.newInstance("", ""); //call constructor
	         return instance;
	     }
	     catch(Exception ex) {
	         ex.printStackTrace();
	         return null;
	     }
	}
}
