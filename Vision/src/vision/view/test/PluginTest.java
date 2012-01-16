package vision.view.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;


import org.junit.Test;

import com.jme3.app.SimpleApplication;

import vision.model.Model;
import vision.model.Sample;
import vision.model.Sensor;
import vision.view.HeaterPlugin;
import vision.view.View;

public class PluginTest {

	@Test
	public void test() {
		SimpleApplication app = new SimpleApplication() {
			
			@Override
			public void simpleInitApp() {
				// TODO Auto-generated method stub
				
			}
		};
		Model m = null;
		try {
			m = new Model(new View());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sample sensor1Sample = new Sample("Temperatur", "Celsius", 20, 1);
		List<Sample> sensor1Messwerte = null;
		sensor1Messwerte.add(sensor1Sample);
		Sample sensor2Sample = new Sample("Temperatur", "Celsius", 22, 2);
		List<Sample> sensor2Messwerte = null;
		sensor2Messwerte.add(sensor2Sample);
		Sensor sensor1 = new Sensor("0001", 0, sensor1Messwerte);
		Sensor sensor2 = new Sensor("0002", 0, sensor2Messwerte);
		HeaterPlugin p = new HeaterPlugin(m, null);
		p.update(app);
		
		List<Sensor> sl = new ArrayList<Sensor>();
		sl.add(new Sensor("test", 100, Collections.EMPTY_LIST));
		
		m.setSensor(sl);
		
		p.update(app);
		
		assertEquals(1, 1);
	}

}
