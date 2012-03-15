package vision.view.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;

import net.java.games.util.plugins.Plugin;

import org.junit.Test;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.DesktopAssetManager;

import vision.model.Model;
import vision.model.Position;
import vision.model.Sample;
import vision.model.Sensor;
import vision.view.HeaterPlugin;
import vision.view.View;

public class PluginTest {

	@Test
	public void testHeaterPluginInitialize() {
		Model m = null;
		View v = null;
		try {
			m = new Model(v = new View());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Application app = new Application();
		AppStateManager stateManager = new AppStateManager(app);
		HeaterPlugin heaterPlugin = new HeaterPlugin(m, v);
		heaterPlugin.initialize(stateManager, app);
		
		assertNotNull(heaterPlugin);
	}
	
	@Test
	public void testHeaterPLugin(){
		Sample sample1 = new Sample("heater", "celsius", 20, 0);
		Sample sample2 = new Sample("heater", "celsius", 21, 0);
		Model m = null;
		View v = new View();
		try {
			m = new Model(v);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		List<Sensor> list = new ArrayList<Sensor>();
		List<Sample> sensor1list = new ArrayList<Sample>();
		sensor1list.add(sample1);
		Sensor sensor1 = new Sensor("sensor1", 0, sensor1list);
		List<Sample> sensor2list = new ArrayList<Sample>();
		sensor2list.add(sample2);
		Sensor sensor2 = new Sensor("sensor2", 0, sensor2list);
		list.add(sensor1);
		list.add(sensor2);
		m.setSensor(list);
		Position sensor1pos = new Position(0, 0, 0);
		sensor1.setPosition(sensor1pos);
		Position sensor2pos = new Position(10, 1, 1);
		sensor2.setPosition(sensor2pos);
		HeaterPlugin heaterPlugin = new HeaterPlugin(m, v);
		Application app = new Application();
		app.setAssetManager(new DesktopAssetManager(true));
		AppStateManager stateManager = new AppStateManager(app);
		heaterPlugin.initialize(stateManager, app);
		heaterPlugin.setDaten(m);
		heaterPlugin.update(20);
		
		assertNotNull(m);
		assertNotNull(v);
	}

}
