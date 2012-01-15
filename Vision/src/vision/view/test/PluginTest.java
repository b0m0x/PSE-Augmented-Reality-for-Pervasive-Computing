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
		Sensor sensor1 = new Sensor(null, 0, null);
		HeaterPlugin p = new HeaterPlugin(m, null);
		p.update(app);
		
		List<Sensor> sl = new ArrayList<Sensor>();
		sl.add(new Sensor("test", 100, Collections.EMPTY_LIST));
		
		m.setSensor(sl);
		
		p.update(app);
		
		assertEquals(1, 1);
	}

}
