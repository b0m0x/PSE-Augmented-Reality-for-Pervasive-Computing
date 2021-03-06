package vision.view.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import vision.model.Model;
import vision.model.Position;
import vision.model.Sample;
import vision.model.Sensor;
import vision.view.HeaterPlugin;
import vision.view.View;
import vision.view.WindowPlugin;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.DesktopAssetManager;

public class PluginTest {
	
	@Test
	public void testHeaterPluginInitialize() {
		View v = new View();
		Model m = Model.createModel(v);
		
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
		View v = new View();
		Model m = Model.createModel(v);
		List<Sensor> list = new ArrayList<Sensor>();
		List<Sample> sensor1list = new ArrayList<Sample>();
		sensor1list.add(sample1);
		Sensor sensor1 = new Sensor("sensor1", 0, sensor1list);
		sensor1.setPosition(new Position(0, 0, 0));
		List<Sample> sensor2list = new ArrayList<Sample>();
		sensor2list.add(sample2);
		Sensor sensor2 = new Sensor("sensor2", 0, sensor2list);
		sensor2.setPosition(new Position(10, 0, 0));
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
		Sample sample3 = new Sample("heater", "celsius", 22, 0);
		list.remove(sensor1);
		sensor1.addToSamples(sample3);
		list.add(sensor1);
		m.setSensor(list);
		try {
			m.getGroundplan().load();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		heaterPlugin.stateAttached(stateManager);
		heaterPlugin.update(40);
		Sample sample4 = new Sample("heater", "celsius", 25, 20);
		List<Sample> sensor3List = new ArrayList<Sample>();
		sensor3List.add(sample4);
		Sensor sensor3 = new Sensor("sensor3", 20, sensor3List);
		list.add(sensor3);
		m.setSensor(list);
		heaterPlugin.stateDetached(stateManager);
		
		assertNotNull(m);
		assertNotNull(v);
		assertNotNull(heaterPlugin);
	}
	
	@Test
	public void testWindowPlugin() {
		Sample sample1 = new Sample("window", "", 100, 0);
		Sample sample2 = new Sample("light", "", 1, 0);
		View v = new View();
		Model m = Model.createModel(v);
		List<Sensor> list = new ArrayList<Sensor>();
		List<Sample> sensor1list = new ArrayList<Sample>();
		sensor1list.add(sample1);
		Sensor sensor1 = new Sensor("sensor1", 0, sensor1list);
		sensor1.setPosition(new Position(0, 0, 0));
		List<Sample> sensor2list = new ArrayList<Sample>();
		sensor2list.add(sample2);
		Sensor sensor2 = new Sensor("sensor2", 0, sensor2list);
		sensor2.setPosition(new Position(10, 0, 0));
		list.add(sensor1);
		list.add(sensor2);
		m.setSensor(list);
		Position sensor1pos = new Position(0, 0, 0);
		sensor1.setPosition(sensor1pos);
		Position sensor2pos = new Position(10, 1, 1);
		sensor2.setPosition(sensor2pos);
		WindowPlugin windowPlugin = new WindowPlugin(m, v);
		Application app = new Application();
		app.setAssetManager(new DesktopAssetManager(true));
		AppStateManager stateManager = new AppStateManager(app);
		windowPlugin.initialize(stateManager, app);
		windowPlugin.setDaten(m);
		windowPlugin.update(20);
		Sample sample3 = new Sample("heater", "celsius", 22, 0);
		list.remove(sensor1);
		sensor1.addToSamples(sample3);
		list.add(sensor1);
		m.setSensor(list);
		try {
			m.getGroundplan().load();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		windowPlugin.stateAttached(stateManager);
		windowPlugin.update(40);
		Sample sample4 = new Sample("heater", "celsius", 25, 20);
		List<Sample> sensor3List = new ArrayList<Sample>();
		sensor3List.add(sample4);
		Sensor sensor3 = new Sensor("sensor3", 20, sensor3List);
		list.add(sensor3);
		m.setSensor(list);
		windowPlugin.stateDetached(stateManager);
		
		assertNotNull(m);
		assertNotNull(v);
		assertNotNull(windowPlugin);
	}

}
